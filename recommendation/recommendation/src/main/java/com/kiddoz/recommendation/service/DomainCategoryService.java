package com.kiddoz.recommendation.service;

import com.kiddoz.recommendation.model.DomainCategory;
import com.kiddoz.recommendation.repository.DomainCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DomainCategoryService {
    private DomainCategoryRepository domainCategoryRepository;

    public DomainCategoryService(DomainCategoryRepository domainCategoryRepository) {
        this.domainCategoryRepository = domainCategoryRepository;
    }

    public DomainCategory addDomainCategory(String name) {
        DomainCategory newDomainCategory = new DomainCategory(null, name);
        return domainCategoryRepository.save(newDomainCategory);
    }

    public List<DomainCategory> getDomainCategory() {
        return this.domainCategoryRepository.findAll();
    }

    public void deleteDomainCategory(Integer id) {
        this.domainCategoryRepository.deleteById(id);
    }
}
