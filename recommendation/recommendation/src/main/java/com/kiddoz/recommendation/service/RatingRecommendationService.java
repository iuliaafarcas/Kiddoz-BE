package com.kiddoz.recommendation.service;


import com.kiddoz.recommendation.model.Parent;
import com.kiddoz.recommendation.model.RatingRecommendation;
import com.kiddoz.recommendation.repository.ApplicationUserRepository;
import com.kiddoz.recommendation.repository.RatingRecommendationRepository;
import com.kiddoz.recommendation.repository.RecommendationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
            if (existsRecommendationRating(recommendationId, parentId) != null) {
                RatingRecommendation oldRating = existsRecommendationRating(recommendationId, parentId);
                oldRating.setNoStars(noStars);
                return this.ratingRecommendationRepository.save(oldRating);
            } else {
                RatingRecommendation ratingRecommendation = new RatingRecommendation(null, recommendation, (Parent) parent, noStars);
                return this.ratingRecommendationRepository.save(ratingRecommendation);
            }
        } else throw new RuntimeException("Specialists cannot add a rating");
    }

    public RatingRecommendation existsRecommendationRating(Integer recommendationId, Integer parentId) {
        for (RatingRecommendation rating : ratingRecommendationRepository.findAll()) {
            if (Objects.equals(rating.getParent().getId(), parentId) && Objects.equals(rating.getRecommendation().getId(), recommendationId))
                return rating;
        }
        return null;
    }

    public List<RatingRecommendation> getRecommendations() {
        return this.ratingRecommendationRepository.findAll();
    }

    public Float getRatingForRecommendation(Integer id) {
        return this.ratingRecommendationRepository.getRatingForRecommendation(id);
    }
}
