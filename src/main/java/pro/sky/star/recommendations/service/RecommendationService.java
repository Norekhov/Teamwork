package pro.sky.star.recommendations.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pro.sky.star.recommendations.model.*;
import pro.sky.star.recommendations.repository.RecommendationRepository;
import pro.sky.star.recommendations.repository.TransactionsRepository;

import java.util.Collection;
import java.util.UUID;

@Service
public class RecommendationService {
    private final Logger logger = LoggerFactory.getLogger(RecommendationService.class);

    private final JdbcTemplate jdbcTemplate;
    private final RulesService rulesService;
    private final RecommendationRepository recommendationRepository;

    public RecommendationService(RulesService rulesService, RecommendationRepository recommendationRepository, JdbcTemplate jdbcTemplate) {
        this.rulesService = rulesService;
        this.recommendationRepository = recommendationRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserRecommendationsResponse getUserRecommendations(UUID userId) {
        Collection<RecommendationRule> rules = rulesService.getAllRules();
        jdbcTemplate.execute("""
                DROP TABLE IF EXISTS CTE;
                CREATE TEMPORARY TABLE CTE NOT PERSISTENT AS
                SELECT t.TYPE TRANSACTION_TYPE, t.AMOUNT TRANSACTION_AMOUNT, p.TYPE PRODUCT_TYPE, p.NAME PRODUCT_NAME
                FROM TRANSACTIONS t JOIN PRODUCTS p ON t.PRODUCT_ID=p.ID where USER_ID='
                """+userId+"';");
        StringBuilder sql = new StringBuilder("WITH rules_cte AS(SELECT C1 as TRUE_RULE FROM(");
        boolean isFirst = true;
        for (RecommendationRule rule : rules) {
            if (!isFirst) {
                sql.append(" UNION ");
            } else {
                isFirst = false;
            }
            sql.append("SELECT CASE WHEN ").append(rule.isNegate() ? "NOT " : "").append("EXISTS (");
            switch (rule.getQuery()) {
                case USER_OF, ACTIVE_USER_OF -> {
                    sql.append("SELECT * FROM CTE WHERE PRODUCT_TYPE = '").append(rule.getArguments().get(0)).append("' LIMIT 1");
                }
                case TRANSACTION_SUM_COMPARE -> {
                    sql.append("SELECT SUM(TRANSACTION_AMOUNT) savings FROM CTE WHERE PRODUCT_TYPE = '")
                            .append(rule.getArguments().get(0))
                            .append("' AND TRANSACTION_TYPE = '")
                            .append(rule.getArguments().get(1))
                            .append("' HAVING savings ")
                            .append(rule.getArguments().get(2))
                            .append(rule.getArguments().get(3));
                }
                case TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW -> {
                    sql.append("SELECT * FROM ( (SELECT SUM(TRANSACTION_AMOUNT) deposits FROM CTE WHERE PRODUCT_TYPE = '")
                            .append(rule.getArguments().get(0))
                            .append("' AND TRANSACTION_TYPE = 'DEPOSIT' ) JOIN (")
                            .append("SELECT SUM(TRANSACTION_AMOUNT) withdraws FROM CTE WHERE PRODUCT_TYPE ='")
                            .append(rule.getArguments().get(0))
                            .append("' AND TRANSACTION_TYPE = 'WITHDRAW' ) )")
                            .append("WHERE deposits ")
                            .append(rule.getArguments().get(1))
                            .append(" withdraws");
                }
            }
            sql.append(") THEN ").append(rule.getId()).append(" END ");
        }
        sql.append(")) SELECT * FROM RECOMMENDATIONS r WHERE r.ID NOT IN (SELECT DISTINCT RECOMMENDATION_ID FROM RECOMMENDATIONS_RULE LEFT JOIN rules_cte ON rule_id=true_rule WHERE true_rule IS NULL);");
        logger.warn(sql.toString());
        var result = new UserRecommendationsResponse();
        result.setUserId(userId);
        result.setRecommendationList(jdbcTemplate.query(sql.toString(),new RecommendationMapper()));
        return result;
    }

    public void deleteRecommendation(UUID recommendationId) {
    }

    public GetAllRecommendationsResponse getAllRecommendations() {
        return new GetAllRecommendationsResponse(recommendationRepository.findAll());
    }

    public Recommendation addRecommendation(Recommendation recommendation) {
//        List<RecommendationRule> recommendationRuleList = recommendation.getRule();
//        for (RecommendationRule recommendationRule : recommendationRuleList) {
//            recommendationRulesRepository.save(recommendationRule);
//        }
        return recommendationRepository.save(recommendation);
    }

}
