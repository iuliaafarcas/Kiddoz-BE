package com.kiddoz.recommendation.repository;

import com.kiddoz.recommendation.model.RatingSpecialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingSpecialistRepository extends JpaRepository<RatingSpecialist, Integer> {
    @Query("select coalesce(avg(rs.noStars), 0)  from RatingSpecialist rs where rs.specialist.id=:id")
    Float getRatingForSpecialist(@Param("id") Integer id);
}
