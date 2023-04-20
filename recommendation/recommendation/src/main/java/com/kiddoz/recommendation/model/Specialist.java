package com.kiddoz.recommendation.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    private String description;

    @Column
    private String domainOfActivities;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    @ManyToOne
    @JoinColumn(name = "domain_id", nullable = false)
    private DomainCategory domain;

    public Specialist(Integer id, String name, String email, String description,
                      String occupation, String quote, DomainCategory domain, String image,
                      String domainOfActivities, Date birthday) {
        super(id, name, email);
        this.description = description;
        this.occupation = occupation;
        this.quote = quote;
        this.domain = domain;
        this.image = image;
        this.domainOfActivities = domainOfActivities;
        this.birthday = birthday;
    }
}

