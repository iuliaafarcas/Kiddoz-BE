package com.kiddoz.recommendation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingSpecialist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "specialist_id", nullable = false)
    private Specialist specialist;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent parent;

    @Column
    private Integer noStars;


}
