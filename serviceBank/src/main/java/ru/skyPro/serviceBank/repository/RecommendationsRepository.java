package ru.skyPro.serviceBank.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getRandomTransactionAmount(UUID user) {
        Integer result = jdbcTemplate.queryForObject(
                "select amount from transactions t where t.user_id = ? limit 1",
                Integer.class,
                user);
        return result != null ? result : 0;
    }

    public int getInvest500Recommendation(UUID user) {
        try {
            Integer result = jdbcTemplate.queryForObject(
                    "select count(t.id) FROM TRANSACTIONS t " +
                            " JOIN PRODUCTS p ON t.product_id = p.id" +
                            " WHERE t.user_id = ?" +
                            " HAVING" +
                            "    SUM(CASE WHEN p.type = 'DEBIT' THEN 1 ELSE 0 END) > 0 AND " +
                            "    SUM(CASE WHEN p.type = 'INVEST' THEN 1 ELSE 0 END) = 0 AND" +
                            "    SUM(CASE WHEN p.type = 'SAVING' AND t.amount > 0 THEN t.amount ELSE 0 END) > 1000", Integer.class, user);
            return result != null ? result : 0;
        } catch (EmptyResultDataAccessException ex) {
            return 0;  // Обработка случая, когда нет результатов
        }
    }

    public int getRecommendedSimpleCredit(UUID user) {
        try {
            Integer result = jdbcTemplate.queryForObject(
                    "SELECT COUNT(t.id) FROM TRANSACTIONS t" +
                            " JOIN PRODUCTS p ON t.product_id = p.id" +
                            " WHERE t.user_id = ?" +
                            " GROUP BY t.user_id" +
                            " HAVING" +
                            " SUM(CASE WHEN p.type = 'CREDIT' THEN 1 ELSE 0 END) = 0 AND" +
                            " SUM(CASE WHEN t.type = 'DEPOSIT' THEN t.amount ELSE 0 END) > SUM(CASE WHEN p.type = 'WITHDRAW' THEN t.amount ELSE 0 END)  AND" +
                            " SUM(CASE WHEN p.type = 'DEBIT' THEN t.amount ELSE 0 END) > 100000", Integer.class, user);
            return result != null ? result : 0;
        } catch (EmptyResultDataAccessException ex) {
            return 0;  // Обработка случая, когда нет результатов
        }
    }

    public int getRecommendedTopSaving(UUID user) {
        try {
            Integer result = jdbcTemplate.queryForObject(
                    "SELECT COUNT(t.id)" +
                            " FROM transactions t" +
                            " JOIN products p ON t.product_id = p.id" +
                            " WHERE t.user_id = ?" +
                            " GROUP BY t.user_id" +
                            " HAVING" +
                            " SUM(CASE WHEN p.type = 'DEBIT' THEN 1 ELSE 0 END) > 0 AND" +
                            " ( " +
                            " SUM(CASE WHEN p.type = 'DEBIT' THEN t.amount ELSE 0 END) >= 50000 OR" +
                            " SUM(CASE WHEN p.type = 'SAVING' THEN t.amount ELSE 0 END) >= 50000" +
                            " ) AND " +
                            " SUM(CASE WHEN p.type = 'DEBIT' THEN t.amount ELSE 0 END) > " +
                            " SUM(CASE WHEN p.type = 'WITHDRAW' THEN t.amount ELSE 0 END)", Integer.class, user);
            return result != null ? result : 0;
        } catch (EmptyResultDataAccessException ex) {
            return 0;  // Обработка случая, когда нет результатов
        }
    }
}
