package pro.sky.star.recommendations.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import pro.sky.star.recommendations.model.Recommendation;
import pro.sky.star.recommendations.model.RecommendationRule;
import pro.sky.star.recommendations.model.RecommendationRuleset;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class RecommendationsRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Recommendation> getAllRecommendations(UUID userId) {
        SqlParameterSource userIdParameter = new MapSqlParameterSource("user_id", userId);
        jdbcTemplate.queryForObject("""
                DROP TABLE IF EXISTS CTE;
                CREATE TEMPORARY TABLE CTE NOT PERSISTENT AS
                SELECT t.TYPE TRANSACTION_TYPE, t.AMOUNT TRANSACTION_AMOUNT, p.TYPE PRODUCT_TYPE, p.NAME PRODUCT_NAME
                FROM TRANSACTIONS t JOIN PRODUCTS p ON t.PRODUCT_ID=p.ID where USER_ID=:userId;
                """,
                userIdParameter,
                Boolean.class);
        List<Recommendation> recommendations = jdbcTemplate.query("SELECT * FROM RECOMMENDATIONS", new RecommendationMapper());
        List<RecommendationRuleset> recommendationsRuleset = jdbcTemplate.query("SELECT * FROM RULESETS", new RecommendationRulesetMapper());
        List<RecommendationRule> rules = jdbcTemplate.query("SELECT * FROM RULES", new RecommendationRuleMapper());
        List<UUID> failedRules = new ArrayList<>();
        for (var rule : rules) {
            Boolean ruleResult = jdbcTemplate.queryForObject(rule.query, userIdParameter,Boolean.class);
            if (!ruleResult) {
                failedRules.add(rule.id);
            }
            System.out.println(rule.description +" is " + ruleResult);
        }
        Set<UUID> failedRecommendations = recommendationsRuleset.stream().filter(r->failedRules.contains(r.ruleId)).map(r->r.recommendationId).collect(Collectors.toSet());
        for (int i = recommendations.size()-1; i+1 > 0 ; i--) {
            if(failedRecommendations.contains(recommendations.get(i).id)){
                recommendations.remove(i);
            }
        }
        System.out.println(rules);
        return recommendations;
    }
}
