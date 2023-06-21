package com.kiddoz.recommendation.repository;


import com.kiddoz.recommendation.model.DomainInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainInterestRepository extends JpaRepository<DomainInterest, Integer> {
}
