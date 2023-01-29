package com.aggiegeeks.financial.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "watched_videos")
@Getter
@Setter
@Data
public class WatchedVideos {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "watched_video_id")
    private long watchedVideoId;
}
