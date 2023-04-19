package com.kiddoz.recommendation.repository;

import com.kiddoz.recommendation.model.RatingRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRecommendationRepository extends JpaRepository<RatingRecommendation, Integer> {

    @Query("select coalesce(avg(rr.noStars), 0)  from RatingRecommendation rr where rr.recommendation.id=:id")
    Float getRatingForRecommendation(@Param("id") Integer id);
}
