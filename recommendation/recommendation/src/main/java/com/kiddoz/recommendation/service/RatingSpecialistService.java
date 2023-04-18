package com.kiddoz.recommendation.service;


import com.kiddoz.recommendation.model.Parent;
import com.kiddoz.recommendation.model.RatingSpecialist;
import com.kiddoz.recommendation.model.Specialist;
import com.kiddoz.recommendation.repository.ApplicationUserRepository;
import com.kiddoz.recommendation.repository.RatingSpecialistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
            if (existsSpecialistRating(specialistId, parentId) != null) {
                RatingSpecialist oldRating = existsSpecialistRating(specialistId, parentId);
                oldRating.setNoStars(noStars);
                return this.ratingSpecialistRepository.save(oldRating);
            } else {
                RatingSpecialist ratingSpecialist = new RatingSpecialist(null, (Specialist) specialist, (Parent) parent, noStars);
                return this.ratingSpecialistRepository.save(ratingSpecialist);
            }
        } else throw new RuntimeException("Specialists cannot add a rating");

    }

    public RatingSpecialist existsSpecialistRating(Integer specialistId, Integer parentId) {
        for (RatingSpecialist rating : ratingSpecialistRepository.findAll()) {
            if (Objects.equals(rating.getParent().getId(), parentId) && Objects.equals(rating.getSpecialist().getId(), specialistId))
                return rating;
        }
        return null;
    }

    public List<RatingSpecialist> getRatingSpecialists() {
        return this.ratingSpecialistRepository.findAll();
    }

    public Float getRatingForSpecialist(Integer id) {
        Float sum = 0f;
        int count = 0;
        for (RatingSpecialist rating : this.getRatingSpecialists()) {
            if (Objects.equals(rating.getSpecialist().getId(), id)) {
                sum += rating.getNoStars();
                count++;
            }
        }
        if (count == 0) {
            return 0f; // No ratings for this recommendation
        } else {
            return sum / count; // Compute average rating
        }
    }


}
