package com.kiddoz.recommendation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Parent extends ApplicationUser {

    public Parent(Integer id, String firstName, String lastName, String email) {
        super(id, firstName, lastName, email);
    }

    public Parent() {
    }
}
