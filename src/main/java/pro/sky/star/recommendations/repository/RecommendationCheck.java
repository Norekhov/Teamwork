package pro.sky.star.recommendations.repository;

import pro.sky.star.recommendations.model.Recommendation;

import java.util.UUID;

public interface RecommendationCheck {

    Recommendation check(UUID id);
}
