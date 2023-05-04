package com.kiddoz.recommendation.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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


    @Temporal(TemporalType.DATE)
    private Date birthday;

    @ManyToOne
    @JoinColumn(name = "domain_id", nullable = false)
    private DomainCategory domain;

    @ManyToMany
    private List<DomainInterest> domainsInterest;

    public Specialist(Integer id, String name, String email, String password, String description,
                      String occupation, String quote, DomainCategory domain, String image,
                      List<DomainInterest> domainsInterest, Date birthday) {
        super(id, name, email, password);
        this.description = description;
        this.occupation = occupation;
        this.quote = quote;
        this.domain = domain;
        this.image = image;
        this.domainsInterest = domainsInterest;
        this.birthday = birthday;
    }
}

