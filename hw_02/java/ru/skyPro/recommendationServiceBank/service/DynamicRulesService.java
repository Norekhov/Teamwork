package ru.skyPro.recommendationServiceBank.service;


import org.springframework.stereotype.Service;
import ru.skyPro.recommendationServiceBank.dto.DynamicRuleDto;
import ru.skyPro.recommendationServiceBank.model.rules.Rule;
import ru.skyPro.recommendationServiceBank.repository.RuleRepository;

@Service
public class DynamicRulesService {
    private final RuleRepository ruleRepository;
    private final DynamicRulesService dynamicRulesService;

    public DynamicRulesService(RuleRepository ruleRepository, DynamicRulesService dynamicRulesService, DynamicRulesService dynamicRulesService1) {
        this.ruleRepository = ruleRepository;
        this.dynamicRulesService = dynamicRulesService1;
    }


    public DynamicRuleDto createDynamicRule(String nameFirstProduct, String nameSecondProduct, String nameThirdProduct) {
        Rule firstRule = ruleRepository.findByQueryName(nameFirstProduct);
        Rule secondRule = ruleRepository.findByQueryName(nameSecondProduct);
        Rule thirdRule = ruleRepository.findByQueryName(nameThirdProduct);
        return dynamicRulesService.createDynamicRule(nameFirstProduct, nameSecondProduct, nameThirdProduct);
    }
}
