package com.kiddoz.recommendation.repository;

import com.kiddoz.recommendation.model.RatingSpecialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingSpecialistRepository extends JpaRepository<RatingSpecialist, Integer> {
}
