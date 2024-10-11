package pro.sky.star.recommendations.service;

import org.springframework.stereotype.Service;
import pro.sky.star.recommendations.model.RecommendationsResponseDTO;
import pro.sky.star.recommendations.repository.RecommendationsRepository;

import java.util.UUID;

@Service
public class RecommendationService {
    private final RecommendationsRepository recommendationsRepository;

    public RecommendationService(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    public RecommendationsResponseDTO getRecommendations(UUID userId) {
        RecommendationsResponseDTO result = new RecommendationsResponseDTO();
        result.user_id =userId;
        result.recommendations=recommendationsRepository.getAllRecommendations(userId);
        return result;
    }
}
