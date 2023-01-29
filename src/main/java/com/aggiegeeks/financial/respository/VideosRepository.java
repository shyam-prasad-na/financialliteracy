package com.aggiegeeks.financial.respository;

import com.aggiegeeks.financial.entity.Videos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideosRepository extends JpaRepository<Videos, Long> {
    String findByVideoId(Long videoId);
    Videos findByVideoLink(String videoLink);
}
