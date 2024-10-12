package pro.sky.star.recommendations.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.star.recommendations.model.Model;
import pro.sky.star.recommendations.service.RecommendationService;

@RestController
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping
    public Model get(String id) {
        return recommendationService.get(id);
    }
}
