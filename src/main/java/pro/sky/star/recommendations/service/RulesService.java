package pro.sky.star.recommendations.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.star.recommendations.model.Recommendation;
import pro.sky.star.recommendations.model.RecommendationRule;
import pro.sky.star.recommendations.repository.RecommendationRulesRepository;
import pro.sky.star.recommendations.repository.RecommendationRepository;

import java.util.Collection;

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

    public void deleteRulesRecommendations(long id) {
        if (!recommendationRepository.existsById(id)) {
            throw new RuntimeException("Ошибка! Рекомендация под id= %d не обнаружена".formatted(id));
        }
        recommendationRepository.deleteById(id);
    }

    public RecommendationRule create(RecommendationRule rule) {
        return recommendationRulesRepository.save(rule);
    }
}