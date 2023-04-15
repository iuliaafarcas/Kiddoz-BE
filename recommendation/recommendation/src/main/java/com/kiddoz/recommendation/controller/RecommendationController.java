package com.kiddoz.recommendation.controller;

import com.kiddoz.recommendation.dto.RecommendationAddDto;
import com.kiddoz.recommendation.model.Recommendation;
import com.kiddoz.recommendation.service.RecommendationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("recommendations")
public class RecommendationController {
    private RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping()
    public Recommendation addRecommendation(@RequestBody RecommendationAddDto recommendationAddDto) {
        return recommendationService.addRecommendation(recommendationAddDto.getTitle(), recommendationAddDto.getDescription(),
                recommendationAddDto.getFromAge(), recommendationAddDto.getFromUnitAge(),
                recommendationAddDto.getToAge(), recommendationAddDto.getToUnitAge(), recommendationAddDto.getType(),
                recommendationAddDto.getImage(), recommendationAddDto.getSpecialistId(), recommendationAddDto.getBenefits());
    }

    @GetMapping
    public List<Recommendation> getRecommendations() {
        return recommendationService.getRecommendations();
    }

    @GetMapping("/{id}")
    public Recommendation getRecommendationById(@PathVariable(value = "id") Integer id) {
        return recommendationService.getRecommendationById(id);
    }

    @GetMapping("/paged")
    public List<Object> getRecommendationPaged(@RequestParam(required = false, defaultValue = "10") Integer
                                                       itemCount,
                                               @RequestParam Integer pageNumber) {
        return recommendationService.getRecommendationsPaged(itemCount, pageNumber);
    }

    @GetMapping("/otherRecommendations/{id}")
    public List<Recommendation> getTopSimilarRecommendations(@PathVariable(value = "id") Integer id) {
        return recommendationService.getTopSimilarRecommendations(id);
    }

    //    @GetMapping("/filterRecommendationsByType")
//    public List<Recommendation> filterRecommendationsByType(@RequestBody TypeFilterDto typeFilterDto) {
//        return recommendationService.filterRecommendationsByType(typeFilterDto.getRecommendations(),
//                typeFilterDto.getTypes());
//    }
    @GetMapping("/getRecommendationsBySpecialist/{id}")
    public List<Recommendation> getRecommendationsBySpecialist(@PathVariable(value = "id") Integer id) {
        return this.recommendationService.getRecommendationsBySpecialist(id);
    }


}
