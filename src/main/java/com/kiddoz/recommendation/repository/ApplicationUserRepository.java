package com.kiddoz.recommendation.repository;

import com.kiddoz.recommendation.model.ApplicationUser;
import com.kiddoz.recommendation.model.Parent;
import com.kiddoz.recommendation.model.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Integer>,
        JpaSpecificationExecutor<ApplicationUser> {
    ApplicationUser findApplicationUserByEmail(String email);

    @Query("select p from Parent p where p.id=:id")
    Optional<Parent> getParentById(Integer id);

    @Query("select s from Specialist s where s.id=:id")
    Optional<Specialist> getSpecialistById(Integer id);

}
