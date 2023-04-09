package com.kiddoz.recommendation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationAddDto {
    private String title;
    private String description;
    private Integer fromAge;
    private String fromUnitAge;
    private Integer toAge;
    private String toUnitAge;
    private String type;
    private String image;
    private Integer specialistId;
    private List<Integer> benefits;


}
