package ru.skyPro.norekhovAndHisCommand.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getRandomTransactionAmount(UUID user){
        Integer result=jdbcTemplate.queryForObject(
                "select amount from transactions t where t.user_ud = ? limit 1",
                Integer.class,
                user);
        return result != null ? result : 0;
    }

    public int checkingRulesInDBWithoutProductType(UUID user, String productType) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT " +
                        "    SUM(AMOUNT) " +
                        "FROM " +
                        "    TRANSACTIONS t " +
                        "JOIN " +
                        "    PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                        "WHERE " +
                        "    USER_ID = ?\n" +
                        "    AND p.\"TYPE\" != ?",
                Integer.class, user, productType);
        return result != null ? result : 0;
    }

    public int checkingRulesForTransactionAmountForProductType(UUID user, String productType, String transactionType) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT " +
                        "    SUM(AMOUNT) " +
                        "FROM " +
                        "    TRANSACTIONS t " +
                        "JOIN  " +
                        "    PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                        "WHERE  " +
                        "    USER_ID = ? " +
                        "    AND p.\"TYPE\" = ? " +
                        "    AND t.\"TYPE\" = ?",
                Integer.class, user, productType, transactionType);
        return result != null ? result : 0;
    }
}
