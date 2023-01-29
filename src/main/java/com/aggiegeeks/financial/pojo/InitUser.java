package com.aggiegeeks.financial.pojo;

import lombok.Data;

import java.util.List;

@Data
public class InitUser {

    private List<String> likedVideos;
    private List<String> dislikedVideos;
    private List<String> neutralVideos;
    private long userId;
}
