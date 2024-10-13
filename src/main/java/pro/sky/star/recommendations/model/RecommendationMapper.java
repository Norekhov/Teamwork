package pro.sky.star.recommendations.model;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RecommendationMapper implements RowMapper<Recommendation> {

    @Override
    public Recommendation mapRow(ResultSet rs, int rowNum) throws SQLException {
        Recommendation recommendation = new Recommendation();
        recommendation.id= UUID.fromString(rs.getString("ID"));
        recommendation.product_name= rs.getString("NAME");
        recommendation.product_text = rs.getString("DESCRIPTION");
        return recommendation;
    }
}
