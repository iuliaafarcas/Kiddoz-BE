package com.kiddoz.recommendation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "recommendation_id", nullable = false)
    private Recommendation recommendation;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent parent;

    @Column
    private Integer noStars;
}
