package com.kiddoz.recommendation.service;


import com.kiddoz.recommendation.model.Parent;
import com.kiddoz.recommendation.model.RatingSpecialist;
import com.kiddoz.recommendation.model.Specialist;
import com.kiddoz.recommendation.repository.ApplicationUserRepository;
import com.kiddoz.recommendation.repository.RatingSpecialistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingSpecialistService {
    private RatingSpecialistRepository ratingSpecialistRepository;
    private ApplicationUserRepository applicationUserRepository;

    public RatingSpecialistService(RatingSpecialistRepository ratingSpecialistRepository, ApplicationUserRepository applicationUserRepository) {
        this.ratingSpecialistRepository = ratingSpecialistRepository;
        this.applicationUserRepository = applicationUserRepository;

    }

    public RatingSpecialist addRatingSpecialist(Integer specialistId, Integer parentId, Integer noStars) {
        var specialist = applicationUserRepository.findById(specialistId).orElseThrow(() -> new RuntimeException("Specialist not found. Rating for S not added!"));
        var parent = applicationUserRepository.findById(parentId).orElseThrow(() -> new RuntimeException("User not found. Rating for R not added!"));
        if (parent instanceof Parent) {
            RatingSpecialist ratingSpecialist = new RatingSpecialist(null, (Specialist) specialist, (Parent) parent, noStars);
            return this.ratingSpecialistRepository.save(ratingSpecialist);
        } else throw new RuntimeException("Specialists cannot add a rating");

    }

    public List<RatingSpecialist> getRatingSpecialists() {
        return this.ratingSpecialistRepository.findAll();
    }
}
