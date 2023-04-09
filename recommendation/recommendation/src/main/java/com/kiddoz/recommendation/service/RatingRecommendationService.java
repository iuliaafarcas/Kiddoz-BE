package com.kiddoz.recommendation.service;


import com.kiddoz.recommendation.model.Parent;
import com.kiddoz.recommendation.model.RatingRecommendation;
import com.kiddoz.recommendation.repository.ApplicationUserRepository;
import com.kiddoz.recommendation.repository.RatingRecommendationRepository;
import com.kiddoz.recommendation.repository.RecommendationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingRecommendationService {
    private RatingRecommendationRepository ratingRecommendationRepository;
    private RecommendationRepository recommendationRepository;
    private ApplicationUserRepository applicationUserRepository;

    public RatingRecommendationService(RatingRecommendationRepository ratingRecommendationRepository, RecommendationRepository recommendationRepository, ApplicationUserRepository applicationUserRepository) {
        this.ratingRecommendationRepository = ratingRecommendationRepository;
        this.applicationUserRepository = applicationUserRepository;
        this.recommendationRepository = recommendationRepository;
    }

    public RatingRecommendation addRatingRecommendation(Integer recommendationId, Integer parentId, Integer noStars) {
        var recommendation = recommendationRepository.findById(recommendationId).orElseThrow(() -> new RuntimeException("Recommendation not found. Rating for R not added!"));
        var parent = applicationUserRepository.findById(parentId).orElseThrow(() -> new RuntimeException("User not found. Rating for R not added!"));
        if (parent instanceof Parent) {
            RatingRecommendation ratingRecommendation = new RatingRecommendation(null, recommendation, (Parent) parent, noStars);
            return this.ratingRecommendationRepository.save(ratingRecommendation);
        } else throw new RuntimeException("Specialists cannot add a rating");

    }

    public List<RatingRecommendation> getRecommendations() {
        return this.ratingRecommendationRepository.findAll();
    }
}
