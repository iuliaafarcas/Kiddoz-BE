package com.kiddoz.recommendation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingRecommendationAddDto {
    private Integer recommendationId;
    private Integer parentId;
    private Integer noStars;
}
