package com.kiddoz.recommendation.service;

import com.kiddoz.recommendation.model.Benefit;
import com.kiddoz.recommendation.model.Recommendation;
import com.kiddoz.recommendation.repository.BenefitRepository;
import com.kiddoz.recommendation.repository.RecommendationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BenefitService {
    private BenefitRepository benefitRepository;
    private RecommendationRepository recommendationRepository;

    public BenefitService(BenefitRepository benefitRepository) {
        this.benefitRepository = benefitRepository;
    }

    public Benefit addBenefitService(String name) {
        Benefit newBenefit = new Benefit(null, name, new ArrayList<>());
        return this.benefitRepository.save(newBenefit);
    }

    public List<Benefit> getBenefits() {
        return this.benefitRepository.findAll();
    }

    public List<Recommendation> getRecommendationsByBenefit(Integer id) {
        Benefit benefit = this.benefitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Benefit not found with ID: " + id));

        return this.benefitRepository.findById(id).get().getRecommendations();
    }

}
