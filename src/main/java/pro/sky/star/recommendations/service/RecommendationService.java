package pro.sky.star.recommendations.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.star.recommendations.model.GetAllRecommendationsResponse;
import pro.sky.star.recommendations.model.Recommendation;
import pro.sky.star.recommendations.model.RecommendationRule;
import pro.sky.star.recommendations.model.UserRecommendationsResponse;
import pro.sky.star.recommendations.repository.RecommendationRepository;
import pro.sky.star.recommendations.repository.RecommendationRulesRepository;
import pro.sky.star.recommendations.repository.TransactionsRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class RecommendationService {
    private final Logger logger = LoggerFactory.getLogger(RecommendationService.class);

    private final TransactionsRepository transactionsRepository;
    private final RecommendationRepository recommendationRepository;
    private final RecommendationRulesRepository recommendationRulesRepository;

    public RecommendationService(TransactionsRepository transactionsRepository, TransactionsRepository transactionsRepository1, RecommendationRepository recommendationRepository, RecommendationRulesRepository recommendationRulesRepository) {
        this.transactionsRepository = transactionsRepository1;
        this.recommendationRepository = recommendationRepository;
        this.recommendationRulesRepository = recommendationRulesRepository;
    }

    public UserRecommendationsResponse getUserRecommendations(UUID userId) {
        return new UserRecommendationsResponse();
    }

    public void deleteRecommendation(UUID recommendationId) {
    }

    public GetAllRecommendationsResponse getAllRecommendations() {
        return new GetAllRecommendationsResponse(recommendationRepository.findAll());
    }

    public Recommendation addRecommendation(Recommendation recommendation) {
        if (Objects.isNull(recommendation)) {
            throw new IllegalArgumentException("Условия ошибочны");
        }
        if (recommendation.getRule().isEmpty() || Objects.isNull(recommendation.getRule())) {
            throw new IllegalArgumentException("Условия ошибочны");
        }
        List<RecommendationRule> recommendationRuleList = recommendation.getRule();
        for (RecommendationRule recommendationRule : recommendationRuleList) {
            recommendationRulesRepository.save(recommendationRule);
        }
        return recommendationRepository.save(recommendation);
    }

}
