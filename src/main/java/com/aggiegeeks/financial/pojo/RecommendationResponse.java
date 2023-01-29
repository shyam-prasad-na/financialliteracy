package com.aggiegeeks.financial.pojo;

import lombok.Data;

import java.util.List;

@Data
public class RecommendationResponse {
    private List<Long> videoIds;
    private String userId;
}
