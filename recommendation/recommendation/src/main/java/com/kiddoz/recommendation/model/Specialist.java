package com.kiddoz.recommendation.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Specialist extends ApplicationUser {
    @Column
    private String occupation;

    @Column
    private String quote;

    @Column
    private Integer age;

    @ManyToOne
    @JoinColumn(name = "domain_id", nullable = false)
    private DomainCategory domain;

    public Specialist(Integer id, String firstName, String lastName, String email, String occupation, String quote, Integer age, DomainCategory domain) {
        super(id, firstName, lastName, email);
        this.occupation = occupation;
        this.quote = quote;
        this.age = age;
        this.domain = domain;
    }

    public Specialist() {
    }

}

