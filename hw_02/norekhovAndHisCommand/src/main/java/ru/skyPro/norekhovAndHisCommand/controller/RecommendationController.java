package ru.skyPro.norekhovAndHisCommand.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skyPro.norekhovAndHisCommand.model.Model;
import ru.skyPro.norekhovAndHisCommand.service.RecommendationService;

@RestController
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{id}")
    public Model get(@PathVariable String id) {
        return recommendationService.get(id);
    }
}
