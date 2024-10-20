package pro.sky.star.recommendations.controller;

import org.springframework.web.bind.annotation.*;
import pro.sky.star.recommendations.model.Product;
import pro.sky.star.recommendations.model.Query;
import pro.sky.star.recommendations.service.RulesService;

import java.util.Collection;

@RestController
@RequestMapping("/rule")
public class RulesController {
    private RulesService rulesService;

    public RulesController(RulesService rulesService) {
        this.rulesService = rulesService;
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        System.out.println(product.toString());
        return rulesService.createRulesRecommendations(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        rulesService.deleteRulesRecommendations(id);
    }

    @GetMapping("/query")
    public Collection<Query> getAllQuery() {
        return rulesService.getAllQueryRecommendations();
    }

    @GetMapping
    public Collection<Product> getAllRules() {
        return rulesService.getAllRulesRecommendations();
    }
}
