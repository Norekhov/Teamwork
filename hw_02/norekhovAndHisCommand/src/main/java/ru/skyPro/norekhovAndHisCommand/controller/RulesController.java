package ru.skyPro.norekhovAndHisCommand.controller;

import org.springframework.web.bind.annotation.*;
import ru.skyPro.norekhovAndHisCommand.model.Product;
import ru.skyPro.norekhovAndHisCommand.model.Query;
import ru.skyPro.norekhovAndHisCommand.service.RulesService;

import java.util.Collection;

@RestController
@RequestMapping("/rule")
public class RulesController {
    private final RulesService rulesService;

    public RulesController(RulesService rulesService) {
        this.rulesService = rulesService;
    }

    @PostMapping("/")
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

    @GetMapping("/rules")
    public Collection<Product> getAllRules() {
        return rulesService.getAllRulesRecommendations();
    }
}
