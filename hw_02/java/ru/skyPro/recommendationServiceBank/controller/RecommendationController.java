package ru.skyPro.recommendationServiceBank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skyPro.recommendationServiceBank.model.ClientRecommendation;
import ru.skyPro.recommendationServiceBank.service.RecommendationsService;

import java.util.UUID;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    public final RecommendationsService service;

    public RecommendationController(RecommendationsService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ClientRecommendation getRecommendations(@PathVariable("id") String id) {
        return service.getClientRecommendation(UUID.fromString(id));
    }
}
