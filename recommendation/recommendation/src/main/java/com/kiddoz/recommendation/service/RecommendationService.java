package com.kiddoz.recommendation.service;

import com.kiddoz.recommendation.model.*;
import com.kiddoz.recommendation.repository.ApplicationUserRepository;
import com.kiddoz.recommendation.repository.BenefitRepository;
import com.kiddoz.recommendation.repository.RatingRecommendationRepository;
import com.kiddoz.recommendation.repository.RecommendationRepository;
import com.kiddoz.recommendation.utils.RecommendationSpecifications;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;
    private final ApplicationUserRepository userRepository;
    private final BenefitRepository benefitRepository;

    private final RatingRecommendationRepository ratingRecommendationRepository;


    public RecommendationService(RecommendationRepository recommendationRepository,
                                 ApplicationUserRepository userRepository,
                                 BenefitRepository benefitRepository,
                                 RatingRecommendationRepository ratingRecommendationRepository) {
        this.recommendationRepository = recommendationRepository;
        this.userRepository = userRepository;
        this.benefitRepository = benefitRepository;
        this.ratingRecommendationRepository = ratingRecommendationRepository;
    }

    public Recommendation addRecommendation(String title, String description, Integer fromAge, String fromUnitAge,
                                            Integer toAge, String toUnitAge, String type, String image,
                                            Integer specialistId, List<Integer> benefits) {
        var specialist = userRepository.findById(specialistId).filter(user -> user instanceof Specialist)
                .orElseThrow(() -> new RuntimeException("Specialist not found. ADD RECOMMENDATION!"));
        List<Benefit> listOfBenefits = new ArrayList<>();
        for (var id : benefits) {
            var newBenefit = benefitRepository.findById(id);
            newBenefit.ifPresent(listOfBenefits::add);

        }
        AgeUnit fromAgeUnit;
        AgeUnit toAgeUnit;
        RecommendationType recommendationType;
        try {
            fromAgeUnit = AgeUnit.valueOf(fromUnitAge);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("From age unit not found");
        }
        try {
            toAgeUnit = AgeUnit.valueOf(toUnitAge);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("To age unit not found");
        }
        try {
            recommendationType = RecommendationType.valueOf(type);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Recommendation type not found");
        }
        Recommendation newRecommendation = new Recommendation(null, title, description, fromAge, fromAgeUnit, toAge,
                toAgeUnit, recommendationType, image, (Specialist) specialist, listOfBenefits);

        Recommendation recc = recommendationRepository.save(newRecommendation);
        return recc;
    }

    public List<Recommendation> getRecommendations() {
        return this.recommendationRepository.findAll();
    }

    public Recommendation getRecommendationById(Integer id) {
        var recommendation = recommendationRepository.findById(id).orElseThrow(() -> new RuntimeException("Recommendation not found -> getSpecialistById"));
        if (recommendation != null) return (Recommendation) recommendation;
        else throw new RuntimeException("Recommendation not found");
    }

    public List<Object> getRecommendationsPaged(Integer itemCount, Integer pageNumber) {
        Pageable page = PageRequest.of(pageNumber - 1, itemCount);
        List<Object> result = new ArrayList();
        List<Recommendation> list_ = recommendationRepository.findAll(page).stream().toList();
        result.add(recommendationRepository.findAll().size());
        result.add(list_);
        return result;
    }

    public List<Recommendation> getTopSimilarRecommendations(Integer recommendationId) {
        var recommendation = recommendationRepository.findById(recommendationId); // Get the recommendation by ID
        if (recommendation.isEmpty()) {
            throw new IllegalArgumentException("Invalid recommendation ID"); // Handle invalid ID
        }

        List<Benefit> benefits = recommendation.get().getBenefits(); // Get the benefits of the given recommendation

        Map<Recommendation, Integer> similarityMap = new HashMap<>(); // Map to store recommendation and their similarity count

        for (Benefit benefit : benefits) {
            List<Recommendation> relatedRecommendations = benefit.getRecommendations(); // Get recommendations related to the benefit
            for (Recommendation relatedRecommendation : relatedRecommendations) {
                if (!relatedRecommendation.equals(recommendation.get())) { // Exclude the original recommendation
                    // Update the similarity count in the map
                    similarityMap.put(relatedRecommendation, similarityMap.getOrDefault(relatedRecommendation, 0) + 1);
                }
            }
        }

        // Sort the recommendations based on similarity count in descending order
        List<Map.Entry<Recommendation, Integer>> sortedList = new ArrayList<>(similarityMap.entrySet());
        sortedList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        // Get the top 5 similar recommendations
        return sortedList.stream()
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }


    public List<Recommendation> getRecommendationsBySpecialist(Integer id) {
        return this.recommendationRepository.findAll().stream().filter(element -> element.getSpecialist().getId().equals(id)).
                collect(Collectors.toList());
    }

    public List<Object> searchRecommendations(int itemCount, int pageNumber, String searchQuery) {
        Pageable page = PageRequest.of(pageNumber - 1, itemCount);
        List<Recommendation> searchResults = recommendationRepository.findAll().stream().filter(recommendation -> recommendation.getTitle().toLowerCase().contains(searchQuery.toLowerCase()))
                .toList().subList((pageNumber - 1) * itemCount, pageNumber * itemCount);
        long totalResults = recommendationRepository.findAll().stream()
                .filter(recommendation -> recommendation.getTitle().toLowerCase()
                        .contains(searchQuery.toLowerCase())).count();

        List<Object> result = new ArrayList<>();
        result.add(totalResults);
        result.add(searchResults);
        return result;
    }

    public List<Recommendation> filterRecommendationByTypes(List<Integer> types) {
        return this.recommendationRepository.findAll(RecommendationSpecifications.typeIn(types));
    }


    public List<Object> filter(Integer itemCount, Integer pageNumber, List<Integer> types, Integer fromAge,
                               Integer toAge,
                               Integer fromUnitAge, Integer starNumber,
                               String title) {
        List<Object> finalList = new ArrayList<>();
        List<Specification<Recommendation>> predicates = new ArrayList<>();
        if (types != null) predicates.add(RecommendationSpecifications.typeIn(types));
        if (title != null) predicates.add(RecommendationSpecifications.titleIn(title));
        if (fromAge != null && toAge != null && fromUnitAge != null)
            predicates.add(RecommendationSpecifications.ageBetween(fromAge, toAge, fromUnitAge));
        Specification<Recommendation> specRecommendation = null;
        for (var next : predicates) {
            if (specRecommendation == null) {
                specRecommendation = next;
            } else {
                specRecommendation = specRecommendation.and(next);
            }
        }
        List<Recommendation> resultList;
        if (specRecommendation == null) resultList = this.recommendationRepository.findAll();
        else resultList = this.recommendationRepository.findAll(specRecommendation);

        if (starNumber == null) {
            int fromIndex = Math.min((pageNumber - 1) * itemCount, resultList.size());
            int toIndex = Math.min(pageNumber * itemCount, resultList.size());
            finalList.add(resultList.subList(fromIndex, toIndex).size());
            finalList.add(resultList.subList(fromIndex, toIndex));
        } else {
            List<Recommendation> list_ = resultList.stream().filter(element -> Math.round(ratingRecommendationRepository
                    .getRatingForRecommendation(element.getId())) >= starNumber).collect(Collectors.toList());
            int fromIndex = Math.min((pageNumber - 1) * itemCount, list_.size());
            int toIndex = Math.min(pageNumber * itemCount, list_.size());
            finalList.add(list_.subList(fromIndex, toIndex).size());
            finalList.add(list_.subList(fromIndex, toIndex));
        }
        return finalList;
    }
}



