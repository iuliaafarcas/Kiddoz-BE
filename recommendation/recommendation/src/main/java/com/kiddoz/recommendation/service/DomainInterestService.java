package com.kiddoz.recommendation.service;


import com.kiddoz.recommendation.model.DomainInterest;
import com.kiddoz.recommendation.repository.DomainInterestRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DomainInterestService {
    private DomainInterestRepository domainInterestRepository;

    public DomainInterestService(DomainInterestRepository domainInterestRepository) {
        this.domainInterestRepository = domainInterestRepository;
    }

    public DomainInterest addDomainInterest(String name) {
        DomainInterest newDomainInterest = new DomainInterest(null, name, new ArrayList<>());
        return domainInterestRepository.save(newDomainInterest);
    }

    public List<DomainInterest> getDomainInterest() {
        return this.domainInterestRepository.findAll();
    }

    public void deleteDomainInterest(Integer id) {
        this.domainInterestRepository.deleteById(id);
    }
}
