package com.kiddoz.recommendation.service;

import com.kiddoz.recommendation.model.Benefit;
import com.kiddoz.recommendation.repository.BenefitRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BenefitService {
    private BenefitRepository benefitRepository;

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
}
