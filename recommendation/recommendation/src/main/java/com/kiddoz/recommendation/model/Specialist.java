package com.kiddoz.recommendation.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Specialist extends ApplicationUser {
    @Column
    private String occupation;

    @Column
    private String quote;

    @Column
    private String image;

    @Column
    private Integer age;

    @Column
    private String description;

    @Column
    private String domainOfActivities;

    @ManyToOne
    @JoinColumn(name = "domain_id", nullable = false)
    private DomainCategory domain;

    public Specialist(Integer id, String firstName, String lastName, String email, String description, String occupation, String quote, Integer age, DomainCategory domain, String image, String domainOfActivities) {
        super(id, firstName, lastName, email);
        this.description = description;
        this.occupation = occupation;
        this.quote = quote;
        this.age = age;
        this.domain = domain;
        this.image = image;
        this.domainOfActivities = domainOfActivities;
    }
}

