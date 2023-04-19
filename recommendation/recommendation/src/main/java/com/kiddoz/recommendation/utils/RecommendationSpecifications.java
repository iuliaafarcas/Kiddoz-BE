package com.kiddoz.recommendation.utils;

import com.kiddoz.recommendation.model.Recommendation;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;


public class RecommendationSpecifications {
    public static Specification<Recommendation> typeIn(List<Integer> types) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("type")).value(types);
    }

    public static Specification<Recommendation> ageBetween(Integer fromAge, Integer toAge, Integer fromUnitAge) {
        return (root, query, criteriaBuilder) -> {
            Predicate fromUnitAgePredicate = criteriaBuilder.equal(root.get("fromUnitAge"), fromUnitAge);
            Predicate fromPredicate = criteriaBuilder.between(root.get("fromAge"), fromAge, toAge);
            Predicate toAgePredicate = criteriaBuilder.between(root.get("toAge"), fromAge, toAge);
            return criteriaBuilder.and(fromUnitAgePredicate, criteriaBuilder.or(fromPredicate, toAgePredicate));
        };
    }

    public static Specification<Recommendation> titleIn(String title) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
                "%" + title.toLowerCase() + "%");
    }

}
