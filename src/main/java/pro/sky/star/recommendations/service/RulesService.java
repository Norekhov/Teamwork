package pro.sky.star.recommendations.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.star.recommendations.model.Recommendation;
import pro.sky.star.recommendations.model.RecommendationRule;
import pro.sky.star.recommendations.repository.RecommendationRulesRepository;
import pro.sky.star.recommendations.repository.RecommendationRepository;

import java.util.Collection;
import java.util.Objects;

@Service
public class RulesService {
    private static final Logger logger = LoggerFactory.getLogger(RulesService.class);
    private final RecommendationRepository recommendationRepository;
    private final RecommendationRulesRepository recommendationRulesRepository;

    public RulesService(RecommendationRepository recommendationRepository,
                        RecommendationRulesRepository recommendationRulesRepository) {
        this.recommendationRepository = recommendationRepository;
        this.recommendationRulesRepository = recommendationRulesRepository;
    }

    public Collection<RecommendationRule> getAllRules() {
        return recommendationRulesRepository.findAll();
    }

    public Collection<Recommendation> getAllRulesRecommendations() {
        return recommendationRepository.findAll();
    }

    public void deleteRule(long id) {
        if (!recommendationRulesRepository.existsById(id)) {
            logger.info("Такого правила не существует: {}", id);
        }
        recommendationRulesRepository.deleteById(id);
    }

    public RecommendationRule create(RecommendationRule rule) {
        RecommendationRule oldRule= recommendationRulesRepository.findByQueryAndArgumentsAndNegate(rule.getQuery(), rule.getArguments(), rule.isNegate());
        if (!Objects.isNull(oldRule)) {
            logger.info("Такое правило уже существует: {}", oldRule);
            return oldRule;
        }
        if (Objects.isNull(rule.getId()) || rule.getId()!=0) {
            logger.info("created new rule: {}", rule);
            return recommendationRulesRepository.save(rule);
        }
        logger.info("Updated rule by id: {}", rule);
        return recommendationRulesRepository.save(rule);

    }
}