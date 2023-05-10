package com.kiddoz.recommendation.repository;

import com.kiddoz.recommendation.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Integer>,
        JpaSpecificationExecutor<ApplicationUser> {

}
