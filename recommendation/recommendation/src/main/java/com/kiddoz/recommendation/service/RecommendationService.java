package com.kiddoz.recommendation.service;

import com.kiddoz.recommendation.model.*;
import com.kiddoz.recommendation.repository.ApplicationUserRepository;
import com.kiddoz.recommendation.repository.BenefitRepository;
import com.kiddoz.recommendation.repository.RecommendationRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;
    private final ApplicationUserRepository userRepository;
    private final BenefitRepository benefitRepository;

    public RecommendationService(RecommendationRepository recommendationRepository, ApplicationUserRepository userRepository, BenefitRepository benefitRepository) {
        this.recommendationRepository = recommendationRepository;
        this.userRepository = userRepository;
        this.benefitRepository = benefitRepository;
    }

    public Recommendation addRecommendation(String title, String description, Integer fromAge, String fromUnitAge,
                                            Integer toAge, String toUnitAge, String type, String image,
                                            Integer specialistId, List<Integer> benefits) {
        var specialist = userRepository.findById(specialistId).filter(user -> user instanceof Specialist)
                .orElseThrow(() -> new RuntimeException("Specialist not found. ADD RECOMMENDATION!"));
        List<Benefit> listOfBenefits = new ArrayList<>();
        for (var id : benefits) {
            var newBenefit = benefitRepository.findById(id);
            newBenefit.ifPresent(listOfBenefits::add);
        }
        AgeUnit fromAgeUnit;
        AgeUnit toAgeUnit;
        RecommendationType recommendationType;
        try {
            fromAgeUnit = AgeUnit.valueOf(fromUnitAge);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("From age unit not found");
        }
        try {
            toAgeUnit = AgeUnit.valueOf(toUnitAge);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("To age unit not found");
        }
        try {
            recommendationType = RecommendationType.valueOf(type);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Recommendation type not found");
        }
        Recommendation newRecommendation = new Recommendation(null, title, description, fromAge, fromAgeUnit, toAge,
                toAgeUnit, recommendationType, image, (Specialist) specialist, listOfBenefits);
        return recommendationRepository.save(newRecommendation);
    }

    public List<Recommendation> getRecommendations() {
        return this.recommendationRepository.findAll();
    }

    public Recommendation getRecommendationById(Integer id) {
        var recommendation = recommendationRepository.findById(id).orElseThrow(() -> new RuntimeException("Recommendation not found -> getSpecialistById"));
        if (recommendation != null) return (Recommendation) recommendation;
        else throw new RuntimeException("Recommendation not found");
    }

    public List<Recommendation> getRecommendationsPaged(Integer itemCount, Integer pageNumber) {
        Pageable page = PageRequest.of(pageNumber, itemCount);
        return recommendationRepository.findAll(page).stream().toList();
    }

}
