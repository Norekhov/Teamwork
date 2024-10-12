package pro.sky.star.recommendations.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.star.recommendations.model.RecommendationsResponse;
import pro.sky.star.recommendations.service.RecommendationService;

import java.util.UUID;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @Operation(summary = "Return some recommendations by user-id")
    @GetMapping(value = "/{userId}")
    public RecommendationsResponse getRecommendations(@PathVariable UUID userId){
        return recommendationService.getRecommendations(userId);
    }

}
