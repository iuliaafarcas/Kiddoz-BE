package com.kiddoz.recommendation.controller;

import com.kiddoz.recommendation.dto.AnswersDto;
import com.kiddoz.recommendation.service.AIService;
import lombok.RequiredArgsConstructor;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("recommendedByAI")
@RequiredArgsConstructor
public class AIController {
    private final AIService aiService;

    @PostMapping
    public void generateRecommendationsByAI(@RequestBody AnswersDto answers) {
        aiService.createSample(answers.getAnswers());
        INDArray prediction = aiService.getPrediction();
        System.out.println(prediction);
    }


}
