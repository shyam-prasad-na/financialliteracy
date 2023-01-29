package com.aggiegeeks.financial.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "videos")
@Getter
@Setter
@Data
public class Videos {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "video_id")
    private long videoId;

    @Column(name = "video_link")
    private String videoLink;
}
