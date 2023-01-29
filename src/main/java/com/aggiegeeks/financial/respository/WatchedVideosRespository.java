package com.aggiegeeks.financial.respository;

import com.aggiegeeks.financial.entity.WatchedVideos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchedVideosRespository extends JpaRepository<WatchedVideos, Long> {
}
