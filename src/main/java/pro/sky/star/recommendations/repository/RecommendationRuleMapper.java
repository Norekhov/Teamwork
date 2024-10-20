package pro.sky.star.recommendations.repository;

import org.springframework.jdbc.core.RowMapper;
import pro.sky.star.recommendations.model.RecommendationRule;
import pro.sky.star.recommendations.model.RecommendationRuleType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RecommendationRuleMapper implements RowMapper<RecommendationRule> {

    @Override
    public RecommendationRule mapRow(ResultSet rs, int rowNum) throws SQLException {
        RecommendationRule rule = new RecommendationRule();
        rule.setId(rs.getLong("ID"));
        rule.setQuery(rs.getObject("QUERY", RecommendationRuleType.class));
        rule.setArguments(rs.getObject("ARGUMENTS", List.class));
        rule.setNegate(rs.getBoolean("NEGATE"));
        rule.setAlternativeRuleId(rs.getLong("ALTERNATIVE_RULE_ID"));
        return rule;
    }
}
