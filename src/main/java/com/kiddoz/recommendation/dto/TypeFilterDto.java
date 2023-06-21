package com.kiddoz.recommendation.dto;

import com.kiddoz.recommendation.model.Recommendation;
import com.kiddoz.recommendation.model.RecommendationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeFilterDto {
    private List<Recommendation> recommendations;
    private List<RecommendationType> types;

}
