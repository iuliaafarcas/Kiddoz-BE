package com.kiddoz.recommendation.controller;

import com.kiddoz.recommendation.model.DomainCategory;
import com.kiddoz.recommendation.service.DomainCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("domainCategory")
public class DomainCategoryController {
    private final DomainCategoryService domainCategoryService;

    public DomainCategoryController(DomainCategoryService domainCategoryService) {
        this.domainCategoryService = domainCategoryService;
    }

    @PostMapping()
    public DomainCategory addDomainCategory(@RequestBody DomainCategory domainCategory) {
        return domainCategoryService.addDomainCategory(domainCategory.getName());
    }

    @GetMapping()
    public List<DomainCategory> getDomainCategory() {
        return domainCategoryService.getDomainCategory();
    }

    @DeleteMapping("/{id}")
    public void deleteDomainCategory(@PathVariable Integer id) {
        domainCategoryService.deleteDomainCategory(id);
    }

}
