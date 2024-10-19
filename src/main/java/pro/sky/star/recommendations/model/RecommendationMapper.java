package pro.sky.star.recommendations.model;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RecommendationMapper implements RowMapper<Recommendation> {

    @Override
    public Recommendation mapRow(ResultSet rs, int rowNum) throws SQLException {
        Recommendation recommendation = new Recommendation();
        recommendation.setProductId(UUID.fromString(rs.getString("ID")));
        recommendation.setProductName(rs.getString("NAME"));
        recommendation.setProductText(rs.getString("DESCRIPTION"));
        return recommendation;
    }
}