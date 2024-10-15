package ru.skyPro.recommendationServiceBank.controller;


import org.springframework.web.bind.annotation.RestController;
import ru.skyPro.recommendationServiceBank.model.rules.DynamicRule;
import ru.skyPro.recommendationServiceBank.service.DynamicRulesService;

@RestController
public class RecommendationByDynamicRulesController {
    private final DynamicRulesService service;

    public RecommendationByDynamicRulesController(DynamicRulesService service) {
        this.service = service;
    }

    public DynamicRule createDynamicRule() {
        return service.createDynamicRule();
    }
}
