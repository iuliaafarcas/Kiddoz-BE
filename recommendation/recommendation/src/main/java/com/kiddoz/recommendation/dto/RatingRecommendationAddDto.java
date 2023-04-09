package com.kiddoz.recommendation.dto;

import com.kiddoz.recommendation.model.Parent;
import com.kiddoz.recommendation.model.Recommendation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingRecommendationAddDto {
    private Recommendation recommendation;
    private Parent parent;
    private Integer noStars;
}
