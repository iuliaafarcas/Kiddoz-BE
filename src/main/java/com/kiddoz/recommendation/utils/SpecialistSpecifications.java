package com.kiddoz.recommendation.utils;

import com.kiddoz.recommendation.model.ApplicationUser;
import com.kiddoz.recommendation.model.DomainCategory;
import com.kiddoz.recommendation.model.Specialist;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;


public class SpecialistSpecifications {
    public static Specification<ApplicationUser> categoryEquals(String domainName) {
        return (root, query, criteriaBuilder) -> {
            Join<Specialist, DomainCategory> specialistDomainCategoryJoin = root.join("domain");
            return criteriaBuilder.equal(specialistDomainCategoryJoin.get("name"), domainName);
        };
    }

    public static Specification<ApplicationUser> isSpecialist() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.type(), Specialist.class);
    }

    public static Specification<ApplicationUser> ageBetween(Integer fromAge, Integer toAge) {
        return (root, query, criteriaBuilder) -> {
            Expression<Integer> birthday = criteriaBuilder.function(
                    "replace", String.class, root.get("birthday").as(String.class),
                    criteriaBuilder.literal("-"), criteriaBuilder.literal("")
            ).as(Integer.class);
            Expression<Integer> today = criteriaBuilder.function(
                    "replace", String.class, criteriaBuilder.currentDate().as(String.class),
                    criteriaBuilder.literal("-"), criteriaBuilder.literal("")
            ).as(Integer.class);
            Expression<Integer> diff = criteriaBuilder.abs(criteriaBuilder.diff(today, birthday));
            Expression<Integer> years = criteriaBuilder.toInteger(
                    criteriaBuilder.quot(diff, Integer.parseInt("10000"))
            );
            if (toAge == null)
                return criteriaBuilder.greaterThanOrEqualTo(years, fromAge);
            if (fromAge == null)
                return criteriaBuilder.lessThanOrEqualTo(years, toAge);
            return criteriaBuilder.between(years, fromAge, toAge);
        };
    }

    public static Specification<ApplicationUser> nameIn(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");

    }


}
