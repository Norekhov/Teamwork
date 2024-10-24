package pro.sky.star.recommendations.controller;

import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Add new rule or update by id. Only unique allowed.")
    @PostMapping
    public RecommendationRule create(@RequestBody RecommendationRule rule) {
        return rulesService.create(rule);
    }

    @Operation(summary = "Delete rule")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        rulesService.deleteRule(id);
    }

    @Operation(summary = "Show all rules")
    @GetMapping
    public Collection<RecommendationRule> getAllQuery() {
        return rulesService.getAllRules();
    }
}
