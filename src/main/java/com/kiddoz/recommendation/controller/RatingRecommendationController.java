package com.kiddoz.recommendation.controller;

import com.kiddoz.recommendation.dto.RatingRecommendationAddDto;
import com.kiddoz.recommendation.model.RatingRecommendation;
import com.kiddoz.recommendation.service.RatingRecommendationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("ratingRecommendations")
public class RatingRecommendationController {
    private final RatingRecommendationService ratingRecommendationService;

    public RatingRecommendationController(RatingRecommendationService ratingRecommendationService) {
        this.ratingRecommendationService = ratingRecommendationService;
    }

    @PostMapping()
    public RatingRecommendation addRatingRecommendation(@RequestBody RatingRecommendationAddDto ratingAddDto) {
        return ratingRecommendationService.addRatingRecommendation(ratingAddDto.getRecommendationId(),
                ratingAddDto.getParentId(), ratingAddDto.getNoStars());
    }

    @GetMapping()
    public List<RatingRecommendation> getRatingRecommendations() {
        return ratingRecommendationService.getRecommendations();
    }

    @GetMapping("/{id}")
    public Float getRatingForRecommendation(@PathVariable Integer id) {
        return this.ratingRecommendationService.getRatingForRecommendation(id);
    }

}
