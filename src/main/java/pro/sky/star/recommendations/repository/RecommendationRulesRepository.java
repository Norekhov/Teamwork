package pro.sky.star.recommendations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.star.recommendations.model.RecommendationRule;
import pro.sky.star.recommendations.model.RecommendationRuleType;

import java.util.List;

@Repository
public interface RecommendationRulesRepository extends JpaRepository<RecommendationRule, Long> {
    RecommendationRule findByQueryAndArgumentsAndNegate(RecommendationRuleType query, List<String> args, boolean negate);
}
