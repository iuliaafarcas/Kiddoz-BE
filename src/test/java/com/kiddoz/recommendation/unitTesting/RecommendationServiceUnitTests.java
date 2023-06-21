package com.kiddoz.recommendation.unitTesting;

import com.kiddoz.recommendation.model.Benefit;
import com.kiddoz.recommendation.model.Recommendation;
import com.kiddoz.recommendation.model.Specialist;
import com.kiddoz.recommendation.repository.ApplicationUserRepository;
import com.kiddoz.recommendation.repository.BenefitRepository;
import com.kiddoz.recommendation.repository.RatingRecommendationRepository;
import com.kiddoz.recommendation.repository.RecommendationRepository;
import com.kiddoz.recommendation.service.AIService;
import com.kiddoz.recommendation.service.BenefitService;
import com.kiddoz.recommendation.service.RecommendationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecommendationServiceUnitTests {

    @InjectMocks
    private RecommendationService recommendationService;
    @Mock
    private RecommendationRepository recommendationRepository;
    @Mock
    private ApplicationUserRepository userRepository;
    @Mock
    private BenefitRepository benefitRepository;
    @Mock
    private BenefitService benefitService;
    @Mock
    private AIService aiService;
    @Mock
    private RatingRecommendationRepository ratingRecommendationRepository;


    @Test
    public void testGetTopSimilarRecommendations_ValidRecommendationIdWithNoRelatedRecommendations() {
        // Arrange
        Integer recommendationId = 2;
        Recommendation recommendation = new Recommendation();
        when(recommendationRepository.findById(recommendationId)).thenReturn(Optional.of(recommendation));
        recommendation.setBenefits(Collections.emptyList());

        // Act
        List<Recommendation> topSimilarRecommendations = recommendationService.getTopSimilarRecommendations(recommendationId);

        // Assert
        assertNotNull(topSimilarRecommendations);
        assertTrue(topSimilarRecommendations.isEmpty());
    }

    @Test
    public void testGetTopSimilarRecommendations_ValidRecommendationIdWithOneRelated() {
        // Arrange
        Integer recommendationId = 3;
        Recommendation recommendation = new Recommendation();
        when(recommendationRepository.findById(recommendationId)).thenReturn(Optional.of(recommendation));
        Recommendation relatedRecommendation = new Recommendation();
        Benefit benefit = new Benefit();
        benefit.setRecommendations(Collections.singletonList(relatedRecommendation));
        recommendation.setBenefits(Collections.singletonList(benefit));

        // Act
        List<Recommendation> topSimilarRecommendations = recommendationService.getTopSimilarRecommendations(recommendationId);

        // Assert
        assertNotNull(topSimilarRecommendations);
        assertEquals(1, topSimilarRecommendations.size());
        assertTrue(topSimilarRecommendations.contains(relatedRecommendation));
    }

    @Test
    void should_get_recommendation_by_id() {
        // Arrange
        Integer id = 1;
        Recommendation recommendation = new Recommendation();
        when(recommendationRepository.findById(id)).thenReturn(Optional.of(recommendation));

        // Act
        Recommendation retrievedRecommendation = recommendationService.getRecommendationById(id);

        // Assert
        assertNotNull(retrievedRecommendation);
        assertEquals(recommendation, retrievedRecommendation);

        verify(recommendationRepository, times(1)).findById(id);
        verifyNoMoreInteractions(recommendationRepository);
    }

    @Test
    void should_get_recommendations_by_specialist() {
        // Arrange
        Integer specialistId = 1;
        Specialist specialist = new Specialist();
        specialist.setId(specialistId);

        Recommendation recommendation1 = new Recommendation();
        recommendation1.setSpecialist(specialist);
        Recommendation recommendation2 = new Recommendation();
        recommendation2.setSpecialist(specialist);
        Recommendation recommendation3 = new Recommendation();
        recommendation3.setSpecialist(specialist);

        List<Recommendation> allRecommendations = Arrays.asList(recommendation1, recommendation2, recommendation3);
        when(recommendationRepository.findAll()).thenReturn(allRecommendations);

        // Act
        List<Recommendation> specialistRecommendations = recommendationService.getRecommendationsBySpecialist(specialistId);

        // Assert
        assertNotNull(specialistRecommendations);
        assertEquals(3, specialistRecommendations.size());
        assertTrue(specialistRecommendations.containsAll(allRecommendations));

        verify(recommendationRepository, times(1)).findAll();
        verifyNoMoreInteractions(recommendationRepository);
    }


}
