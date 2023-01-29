package com.aggiegeeks.financial.services;

import com.aggiegeeks.financial.entity.UserLiked;
import com.aggiegeeks.financial.entity.Users;
import com.aggiegeeks.financial.entity.Videos;
import com.aggiegeeks.financial.entity.WatchedVideos;
import com.aggiegeeks.financial.pojo.*;
import com.aggiegeeks.financial.respository.UserRepository;
import com.aggiegeeks.financial.respository.UsersLikedRepository;
import com.aggiegeeks.financial.respository.VideosRepository;
import com.aggiegeeks.financial.respository.WatchedVideosRespository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FinancialService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private VideosRepository videosRepository;

    @Autowired
    private UsersLikedRepository usersLikedRepository;

    @Autowired
    private WatchedVideosRespository watchedVideosRespository;

    @Autowired
    private UserRepository userRepository;

    @Value("${recommender.model.endpoint}")
    private String recommenderEndpoint;

    public Recommendation getRecommendations(String userId) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String response = restTemplate.exchange(recommenderEndpoint+userId, HttpMethod.GET, entity, String.class).getBody();
        RecommendationResponse recommendationResponse = objectMapper.readValue(response, RecommendationResponse.class);
        List<Long> videoIds = recommendationResponse.getVideoIds();
        List<String> videoLinks = new ArrayList<>();
        for(Long videoId : videoIds) {
            videoLinks.add(videosRepository.findByVideoId(videoId));
        }
        Recommendation recommendation = new Recommendation();
        recommendation.setVideoLinks(videoLinks);
        recommendation.setUserId(userId);
        return recommendation;
    }

    public void processCallback(String string) throws JsonProcessingException {
        Callback callback = objectMapper.readValue(string, Callback.class);

        UserLiked userLiked =  new UserLiked();
        userLiked.setUserId(callback.getUserId());
        userLiked.setRating(callback.getRating());
        userLiked.setVideoId(callback.getVideoId());

        usersLikedRepository.save(userLiked);

        WatchedVideos watchedVideos = new WatchedVideos();
        watchedVideos.setUserId(callback.getUserId());
        watchedVideos.setWatchedVideoId(callback.getVideoId());

        watchedVideosRespository.save(watchedVideos);
    }

    public void initializeUser(String body) throws JsonProcessingException {
        InitUser initUser = objectMapper.readValue(body, InitUser.class);

        for(String videoLink: initUser.getLikedVideos()) {
            Videos video = videosRepository.findByVideoLink(videoLink);
            long videoId = video.getVideoId();
            saveToUserLiked(initUser, videoId, 1);
            saveToWatched(initUser, videoId);
        }

        for(String videoLink: initUser.getDislikedVideos()) {
            Videos video = videosRepository.findByVideoLink(videoLink);
            long videoId = video.getVideoId();
            saveToUserLiked(initUser, videoId, -1);
            saveToWatched(initUser, videoId);
        }

        for(String videoLink: initUser.getNeutralVideos()) {
            Videos video = videosRepository.findByVideoLink(videoLink);
            long videoId = video.getVideoId();
            saveToUserLiked(initUser, videoId, 0);
            saveToWatched(initUser, videoId);
        }
    }

    private void saveToWatched(InitUser initUser, long videoId ){
        WatchedVideos watchedVideos = new WatchedVideos();
        watchedVideos.setUserId(initUser.getUserId());
        watchedVideos.setWatchedVideoId(videoId);
        watchedVideosRespository.save(watchedVideos);
    }

    private void saveToUserLiked(InitUser initUser, long videoId, int rating){
        UserLiked userLiked =  new UserLiked();
        userLiked.setUserId(initUser.getUserId());
        userLiked.setRating(rating);
        userLiked.setVideoId(videoId);
        usersLikedRepository.save(userLiked);
    }


    public boolean login(String body) throws JsonProcessingException {
        LoginData loginData = objectMapper.readValue(body, LoginData.class);
        Users user = userRepository.getUsersByUserName(loginData.getUserName());
        return user.getPassword().trim().equals(loginData.getPassword().trim());
    }
}
