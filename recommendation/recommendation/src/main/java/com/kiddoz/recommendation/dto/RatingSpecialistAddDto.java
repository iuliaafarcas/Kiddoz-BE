package com.kiddoz.recommendation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingSpecialistAddDto {
    private Integer specialistId;
    private Integer parentId;
    private Integer noStars;
}
