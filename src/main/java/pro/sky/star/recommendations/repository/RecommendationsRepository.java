package pro.sky.star.recommendations.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.star.recommendations.model.Recommendation;
import pro.sky.star.recommendations.model.RecommendationMapper;

import java.util.List;
import java.util.UUID;

@Repository
public class RecommendationsRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int checkingRulesInDBWithoutProductType(UUID userId, String productType) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", userId);
        params.addValue("product_type", productType);
        Integer result = jdbcTemplate.queryForObject("""
                SELECT SUM(AMOUNT) FROM PRODUCTS p
                JOIN TRANSACTIONS t ON t.PRODUCT_ID = p.ID
                WHERE USER_ID = :user_id AND p.TYPE != :product_type;
                """, params, Integer.class);
        return result != null ? result : 0;
    }

    public int checkingRulesForTransactionAmountForProductType(UUID userId, String productType, String transactionType) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", userId);
        params.addValue("product_type", productType);
        params.addValue("transaction_type", transactionType);

        Integer result = jdbcTemplate.queryForObject("""
                SELECT SUM(AMOUNT) FROM PRODUCTS p
                JOIN TRANSACTIONS t ON t.PRODUCT_ID = p.ID
                WHERE USER_ID = :user_id AND p.TYPE != :product_type AND t.TYPE = transaction_type;
                """, params, Integer.class);
        return result != null ? result : 0;
    }

    public List<Recommendation> getAllRecommendations() {
        return jdbcTemplate.query("SELECT * FROM RECOMMENDATIONS", new RecommendationMapper());
    }

    public void deleteRecommendation() {
    }
}
