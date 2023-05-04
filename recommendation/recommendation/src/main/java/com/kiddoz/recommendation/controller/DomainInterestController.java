package com.kiddoz.recommendation.controller;

import com.kiddoz.recommendation.model.DomainInterest;
import com.kiddoz.recommendation.service.DomainInterestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("domainInterest")
public class DomainInterestController {
    private final DomainInterestService domainInterestService;

    public DomainInterestController(DomainInterestService domainInterestService) {
        this.domainInterestService = domainInterestService;
    }

    @PostMapping()
    public DomainInterest addDomainInterest(@RequestBody DomainInterest domainInterest) {
        return domainInterestService.addDomainInterest(domainInterest.getName());
    }

    @GetMapping()
    public List<DomainInterest> getDomainInterest() {
        return domainInterestService.getDomainInterest();
    }

    @DeleteMapping("/{id}")
    public void deleteDomainInterest(@PathVariable Integer id) {
        domainInterestService.deleteDomainInterest(id);
    }
}
