package pro.sky.star.recommendations.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.star.recommendations.model.UserDB;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.Map;

@Repository
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;

    private final Map<String, Boolean> listUserOfAndActiveUserOf = new HashMap<>();

    private final Map<String, Boolean> listTransactionSumCompare = new HashMap<>();

    private final Map<String, Boolean> listTransactionSumCompareDepositWithdraw = new HashMap<>();

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int checksIfTheUserHasUsedTheCurrentProductType(UUID user, String productType) {
        return getSumOfTransactions(user, productType, false);
    }


    public int checksTheTransactionAmountForTheSpecifiedProductTypeAndTransaction(UUID user, String productType, String transactionType) {
        return getSumOfTransactions(user, productType, true, transactionType);
    }


    private int getSumOfTransactions(UUID user, String productType, boolean isEqual, String... transactionType) {
        String operator = isEqual ? "=" : "!=";
        String sql = "SELECT SUM(AMOUNT) FROM PRODUCTS p " +
                "JOIN TRANSACTIONS t ON t.PRODUCT_ID = p.ID " +
                "WHERE USER_ID = ? AND p.TYPE " + operator + "?";

        Integer result = jdbcTemplate.queryForObject(sql, Integer.class, user, productType);
        return result != null ? result : 0;
    }


    public boolean checkUserOfAndActiveUserOf(UUID user, String productType, String sumOrCount) {
        SqlUtil.validateProductType(productType);
        SqlUtil.validateSumOrCount(sumOrCount);

        int index = sumOrCount.equals("COUNT") ? 5 : 0;
        String key = user.toString() + productType + sumOrCount;

        if (listUserOfAndActiveUserOf.containsKey(key)) {
            return listUserOfAndActiveUserOf.get(key);
        }

        String sql = String.format("SELECT CASE WHEN %s(AMOUNT) > ? THEN TRUE ELSE FALSE END AS is_amount " +
                "FROM PRODUCTS p JOIN TRANSACTIONS t ON t.PRODUCT_ID = p.ID WHERE USER_ID = ? AND p.TYPE=?", sumOrCount);

        Boolean result = jdbcTemplate.queryForObject(sql, Boolean.class, index, user, productType);
        listUserOfAndActiveUserOf.put(key, result);

        return result != null && result;
    }


    public boolean checkTransactionSumCompare(UUID user, List<String> arg) {
        SqlUtil.validateComparisonArgs(arg);

        String operator = arg.get(2); // Получаем оператор
        SqlUtil.validateOperator(operator);

        String key = user.toString() + arg;

        if (listTransactionSumCompare.containsKey(key)) {
            return listTransactionSumCompare.get(key);
        }

        String sql = String.format(
                "SELECT CASE WHEN SUM(AMOUNT) %s ? THEN TRUE ELSE FALSE END AS is_amount " +
                        "FROM PRODUCTS p JOIN TRANSACTIONS t ON t.PRODUCT_ID = p.ID " +
                        "WHERE USER_ID = ? AND p.TYPE=? AND t.TYPE=?",
                operator
        );

        Boolean result = jdbcTemplate.queryForObject(
                sql,
                Boolean.class,
                arg.get(3),
                user,
                arg.get(0),
                arg.get(1)
        );

        listTransactionSumCompare.put(key, result);

        return result != null && result;
    }


    public boolean checkTransactionSumCompareDepositWithdraw(UUID user, List<String> arg) {
        SqlUtil.validateProductType(arg.get(0));

        String operator = arg.get(1);
        SqlUtil.validateOperator(operator);

        String key = user.toString() + arg;

        if (listTransactionSumCompareDepositWithdraw.containsKey(key)) {
            return listTransactionSumCompareDepositWithdraw.get(key);
        }

        String sql = String.format(
                "SELECT CASE WHEN (SELECT SUM(t.AMOUNT) FROM PRODUCTS p JOIN TRANSACTIONS t ON t.PRODUCT_ID = p.ID " +
                        "WHERE t.TYPE = 'DEPOSIT' AND USER_ID = ? AND p.TYPE = ?) %s " +
                        "(SELECT SUM(t.AMOUNT) FROM PRODUCTS p JOIN TRANSACTIONS t ON t.PRODUCT_ID = p.ID " +
                        "WHERE t.TYPE = 'WITHDRAW' AND USER_ID = ? AND p.TYPE = ?) THEN TRUE ELSE FALSE END AS is_deposit_greater;",
                operator
        );

        Boolean result = jdbcTemplate.queryForObject(
                sql,
                Boolean.class,
                user,
                arg.get(0),
                user,
                arg.get(0)
        );

        listTransactionSumCompareDepositWithdraw.put(key, result);

        return result != null && result;
    }


    public UserDB getIdUser(String userName) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT ID, FIRST_NAME, LAST_NAME FROM USERS u WHERE u.USERNAME = ?",
                    new Object[]{userName},
                    new BeanPropertyRowMapper<>(UserDB.class)
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new RuntimeException("Ошибка доступа к базе данных", e);
        }
    }


    public void clearCaches() {
        listUserOfAndActiveUserOf.clear();
        listTransactionSumCompare.clear();
        listTransactionSumCompareDepositWithdraw.clear();
    }
}
