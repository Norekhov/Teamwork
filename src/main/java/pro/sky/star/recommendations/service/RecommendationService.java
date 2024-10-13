package pro.sky.star.recommendations.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.star.recommendations.model.Recommendation;
import pro.sky.star.recommendations.model.UserRecommendationsResponse;
import pro.sky.star.recommendations.recommendation.RecommendationForInvest500;
import pro.sky.star.recommendations.recommendation.RecommendationForSimpleLoan;
import pro.sky.star.recommendations.recommendation.RecommendationForTopSaving;
import pro.sky.star.recommendations.repository.RecommendationsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RecommendationService {
    private final Logger logger = LoggerFactory.getLogger(RecommendationService.class);

    private final RecommendationsRepository recommendationsRepository;

    private final RecommendationForInvest500 recommendationForInvest500;

    private final RecommendationForSimpleLoan recommendationForSimpleLoan;

    private final RecommendationForTopSaving recommendationForTopSaving;

    public RecommendationService(RecommendationsRepository recommendationsRepository,
                                 RecommendationForInvest500 recommendationForInvest500,
                                 RecommendationForSimpleLoan recommendationForSimpleLoan,
                                 RecommendationForTopSaving recommendationRuleSetTopSaving) {
        this.recommendationsRepository = recommendationsRepository;
        this.recommendationForInvest500 = recommendationForInvest500;
        this.recommendationForSimpleLoan = recommendationForSimpleLoan;
        this.recommendationForTopSaving = recommendationRuleSetTopSaving;
    }

    public UserRecommendationsResponse getRecommendations(UUID id) {
        List<Recommendation> recommendationList = new ArrayList<>();
        recommendationList.add(recommendationForInvest500.check(id));
        recommendationList.add(recommendationForTopSaving.check(id));
        recommendationList.add(recommendationForSimpleLoan.check(id));
        return new UserRecommendationsResponse(id, recommendationList);
    }

    public Recommendation addRecommendation(Recommendation recommendation) {
        return new Recommendation();
    }

    public void deleteRecommendation(UUID recommendationId) {
        recommendationsRepository.deleteRecommendation();
    }

    public List<Recommendation> getAllRecommendations() {
        return recommendationsRepository.getAllRecommendations();
    }
}
