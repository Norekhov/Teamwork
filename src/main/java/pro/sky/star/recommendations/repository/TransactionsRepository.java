package pro.sky.star.recommendations.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.star.recommendations.model.Recommendation;
import pro.sky.star.recommendations.model.RecommendationMapper;

import java.util.List;

@Repository
public class TransactionsRepository {
    private final JdbcTemplate jdbcTemplate;

    public TransactionsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Recommendation> getAllRecommendations() {
        return jdbcTemplate.query("SELECT * FROM RECOMMENDATIONS", new RecommendationMapper());
    }

    public void deleteRecommendation() {
    }
}
