package com.kiddoz.recommendation.repository;

import com.kiddoz.recommendation.model.DomainCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainCategoryRepository extends JpaRepository<DomainCategory, Integer> {
}
