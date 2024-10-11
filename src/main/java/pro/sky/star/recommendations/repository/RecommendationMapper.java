package pro.sky.star.recommendations.repository;

import org.springframework.jdbc.core.RowMapper;
import pro.sky.star.recommendations.model.Recommendation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RecommendationMapper implements RowMapper<Recommendation> {

    @Override
    public Recommendation mapRow(ResultSet rs, int rowNum) throws SQLException {
        Recommendation recommendation = new Recommendation();
        recommendation.id= UUID.fromString(rs.getString("ID"));
        recommendation.name= rs.getString("NAME");
        recommendation.description= rs.getString("DESCRIPTION");
        return recommendation;
    }
}
