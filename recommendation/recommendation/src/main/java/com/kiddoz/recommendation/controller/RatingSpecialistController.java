package com.kiddoz.recommendation.controller;

import com.kiddoz.recommendation.dto.RatingSpecialistAddDto;
import com.kiddoz.recommendation.model.RatingSpecialist;
import com.kiddoz.recommendation.service.RatingSpecialistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ratingSpecialists")
public class RatingSpecialistController {
    private final RatingSpecialistService ratingSpecialistService;

    public RatingSpecialistController(RatingSpecialistService ratingSpecialistService) {
        this.ratingSpecialistService = ratingSpecialistService;
    }

    @PostMapping()
    public RatingSpecialist addRatingSpecialist(@RequestBody RatingSpecialistAddDto ratingAddDto) {
        return ratingSpecialistService.addRatingSpecialist(ratingAddDto.getSpecialistId(), ratingAddDto.getParentId(),
                ratingAddDto.getNoStars());
    }

    @GetMapping
    public List<RatingSpecialist> getRatingSpecialist() {
        return this.ratingSpecialistService.getRatingSpecialists();
    }

}
