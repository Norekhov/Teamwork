package pro.sky.star.recommendations.service;

import org.springframework.stereotype.Service;
import pro.sky.star.recommendations.model.RecommendationsResponse;
import pro.sky.star.recommendations.repository.RecommendationsRepository;

import java.util.UUID;

@Service
public class RecommendationService {
    private final RecommendationsRepository recommendationsRepository;

    public RecommendationService(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    public RecommendationsResponse getRecommendations(UUID userId) {
        RecommendationsResponse result = new RecommendationsResponse();
        result.user_id =userId;
        result.recommendations=recommendationsRepository.getAllRecommendations(userId);
        return result;
    }
}
