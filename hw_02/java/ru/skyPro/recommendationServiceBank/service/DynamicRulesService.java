package ru.skyPro.recommendationServiceBank.service;


import org.springframework.stereotype.Service;
import ru.skyPro.recommendationServiceBank.model.rules.DynamicRule;
import ru.skyPro.recommendationServiceBank.repository.RuleRepository;

@Service
public class DynamicRulesService {
    private final RuleRepository ruleRepository;
    private final DynamicRulesService dynamicRulesService;
    
    public DynamicRulesService(RuleRepository ruleRepository, DynamicRulesService dynamicRulesService, DynamicRulesService dynamicRulesService1) {
        this.ruleRepository = ruleRepository;
        this.dynamicRulesService = dynamicRulesService1;
    }


    public DynamicRule createDynamicRule(String nameProduct) {

    }
}
