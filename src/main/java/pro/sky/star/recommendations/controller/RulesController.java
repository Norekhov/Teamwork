package pro.sky.star.recommendations.controller;

import org.springframework.web.bind.annotation.*;
import pro.sky.star.recommendations.model.RecommendationRule;
import pro.sky.star.recommendations.service.RulesService;

import java.util.Collection;

@RestController
@RequestMapping("/rule")
public class RulesController {
    private final RulesService rulesService;

    public RulesController(RulesService rulesService) {
        this.rulesService = rulesService;
    }

    @PostMapping
    public RecommendationRule create(@RequestBody RecommendationRule rule) {
        return rulesService.create(rule);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        rulesService.deleteRulesRecommendations(id);
    }

    @GetMapping
    public Collection<RecommendationRule> getAllQuery() {
        return rulesService.getAllRules();
    }
}
