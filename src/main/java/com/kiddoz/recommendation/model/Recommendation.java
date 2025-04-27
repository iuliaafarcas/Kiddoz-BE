package com.kiddoz.recommendation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String title;

    @Column(length = 2000)
    private String description;

    @Column
    private Integer fromAge;

    @Column
    private AgeUnit fromUnitAge;

    @Column
    private Integer toAge;

    @Column
    private AgeUnit toUnitAge;

    @Column
    private RecommendationType type;

    @Column
    private String image;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "specialist_id", nullable = false)
    private Specialist specialist;

    @ManyToMany(cascade = {CascadeType.MERGE})
    private List<Benefit> benefits;
}
