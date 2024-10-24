package pro.sky.star.recommendations.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.star.recommendations.exception.EntityNotFoundException;
import pro.sky.star.recommendations.model.RecommendationRule;
import pro.sky.star.recommendations.model.RecommendationRuleType;
import pro.sky.star.recommendations.repository.RecommendationRulesRepository;

import java.util.Collection;
import java.util.Objects;
import java.util.List;

@Service
public class RulesService {
    private static final Logger logger = LoggerFactory.getLogger(RulesService.class);
    private final RecommendationRulesRepository recommendationRulesRepository;

    public RulesService(RecommendationRulesRepository recommendationRulesRepository) {
        this.recommendationRulesRepository = recommendationRulesRepository;
    }

    public RecommendationRule getRule(long id) { return recommendationRulesRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));}
    public boolean checkExistsRuleWithId(long id) {
        if (!recommendationRulesRepository.existsById(id)) {
            logger.info("Такого правила не существует: {}", id);
            return false;
        }
        return true;}

    public Collection<RecommendationRule> getAllRules() {
        return recommendationRulesRepository.findAll();
    }

    public void deleteRule(long id) {
        if (checkExistsRuleWithId(id)){
            logger.info("Правило удалено: {}", id);
            recommendationRulesRepository.deleteById(id);
        }
    }

    public RecommendationRule create(RecommendationRule rule) {
        RecommendationRule oldRule= findByQueryAndArgumentsAndNegateAndAlternativeRuleId(rule.getQuery(), rule.getArguments(), rule.isNegate(), rule.getAlternativeRuleId());
        if (rule.getAlternativeRuleId()==0) {
            rule.setAlternativeRuleId(null);
        }
        if (!Objects.isNull(oldRule)) {
            logger.info("Такое правило уже существует: {}", oldRule);
            return oldRule;
        }
        if (Objects.isNull(rule.getId()) || rule.getId()==0) {
            logger.info("created new rule: {}", rule);
            return recommendationRulesRepository.save(rule);
        }
        logger.info("Updated rule by id: {}", rule);
        return recommendationRulesRepository.save(rule);
    }

    public RecommendationRule findByQueryAndArgumentsAndNegateAndAlternativeRuleId(RecommendationRuleType ruleType, List<String> arguments, boolean negate, Long alternativeRuleId){
        return recommendationRulesRepository.findByQueryAndArgumentsAndNegateAndAlternativeRuleId(ruleType, arguments, negate, alternativeRuleId);
    }
}