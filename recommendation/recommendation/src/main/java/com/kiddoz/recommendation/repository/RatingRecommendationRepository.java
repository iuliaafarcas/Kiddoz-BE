package com.kiddoz.recommendation.repository;

import com.kiddoz.recommendation.model.RatingRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRecommendationRepository extends JpaRepository<RatingRecommendation, Integer> {
}
