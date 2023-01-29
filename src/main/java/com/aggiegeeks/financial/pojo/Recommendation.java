package com.aggiegeeks.financial.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Recommendation {
    private List<String> videoLinks;
    private String userId;
}
