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
    private String image;

    @Column
    private Integer age;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "domain_id", nullable = false)
    private DomainCategory domain;

    public Specialist(Integer id, String firstName, String lastName, String email, String occupation, String quote, Integer age, DomainCategory domain, String image) {
        super(id, firstName, lastName, email);
        this.occupation = occupation;
        this.quote = quote;
        this.age = age;
        this.domain = domain;
        this.image = image;
    }

    public Specialist() {
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public DomainCategory getDomain() {
        return domain;
    }

    public void setDomain(DomainCategory domain) {
        this.domain = domain;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

