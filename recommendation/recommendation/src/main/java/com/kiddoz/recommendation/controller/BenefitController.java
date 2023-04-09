package com.kiddoz.recommendation.controller;

import com.kiddoz.recommendation.model.Benefit;
import com.kiddoz.recommendation.service.BenefitService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("benefits")
public class BenefitController {
    private final BenefitService benefitService;

    public BenefitController(BenefitService benefitService) {
        this.benefitService = benefitService;
    }

    @PostMapping()
    public Benefit addBenefit(@RequestBody Benefit benefit) {
        return benefitService.addBenefitService(benefit.getName());
    }

    @GetMapping()
    public List<Benefit> getBenefits() {
        return benefitService.getBenefits();
    }

}
