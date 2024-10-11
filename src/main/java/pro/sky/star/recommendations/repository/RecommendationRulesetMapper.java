package pro.sky.star.recommendations.repository;

import org.springframework.jdbc.core.RowMapper;
import pro.sky.star.recommendations.model.RecommendationRuleset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RecommendationRulesetMapper implements RowMapper<RecommendationRuleset> {

    @Override
    public RecommendationRuleset mapRow(ResultSet rs, int rowNum) throws SQLException {
        RecommendationRuleset ruleset = new RecommendationRuleset();
        ruleset.id = UUID.fromString(rs.getString("ID"));
        ruleset.recommendationId = UUID.fromString(rs.getString("RECOMMENDATION_ID"));
        ruleset.ruleId = UUID.fromString(rs.getString("RULE_ID"));
        return ruleset;
    }
}
