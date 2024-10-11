package pro.sky.star.recommendations.repository;

import org.springframework.jdbc.core.RowMapper;
import pro.sky.star.recommendations.model.RecommendationRule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RecommendationRuleMapper implements RowMapper<RecommendationRule> {

    @Override
    public RecommendationRule mapRow(ResultSet rs, int rowNum) throws SQLException {
        RecommendationRule rule = new RecommendationRule();
        rule.id= UUID.fromString(rs.getString("ID"));
        rule.query= rs.getString("QUERY");
        rule.description= rs.getString("DESCRIPTION");
        return rule;
    }
}
