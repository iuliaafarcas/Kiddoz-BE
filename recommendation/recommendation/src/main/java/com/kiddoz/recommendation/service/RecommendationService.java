package com.kiddoz.recommendation.service;

import com.kiddoz.recommendation.model.*;
import com.kiddoz.recommendation.repository.ApplicationUserRepository;
import com.kiddoz.recommendation.repository.BenefitRepository;
import com.kiddoz.recommendation.repository.RecommendationRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;
    private final ApplicationUserRepository userRepository;
    private final BenefitRepository benefitRepository;

    public RecommendationService(RecommendationRepository recommendationRepository, ApplicationUserRepository userRepository, BenefitRepository benefitRepository) {
        this.recommendationRepository = recommendationRepository;
        this.userRepository = userRepository;
        this.benefitRepository = benefitRepository;
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
//        for (var benefit : listOfBenefits) {
//            benefit.getRecommendations().add(recc);
//            benefitRepository.save(benefit);
//        }
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

}
