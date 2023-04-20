package com.kiddoz.recommendation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Parent extends ApplicationUser {

    public Parent(Integer id, String name, String email) {
        super(id, name, email);
    }

    public Parent() {
    }
}
