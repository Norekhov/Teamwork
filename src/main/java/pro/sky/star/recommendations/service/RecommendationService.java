package pro.sky.star.recommendations.service;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pro.sky.star.recommendations.model.*;
import pro.sky.star.recommendations.repository.RecommendationRepository;

import java.util.Collection;
import java.util.Objects;
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

    private String createSqlQueryFromRule(RecommendationRule rule){
        StringBuilder result = new StringBuilder();
        result.append("SELECT CASE WHEN ").append(rule.isNegate() ? "NOT " : "").append("EXISTS (");
        switch (rule.getQuery()) {
            case USER_OF, ACTIVE_USER_OF ->
                    result.append("SELECT * FROM CTE WHERE PRODUCT_TYPE = '").append(rule.getArguments().get(0)).append("' LIMIT 1");
            case TRANSACTION_SUM_COMPARE ->
                    result.append("SELECT SUM(TRANSACTION_AMOUNT) savings FROM CTE WHERE PRODUCT_TYPE = '")
                            .append(rule.getArguments().get(0))
                            .append("' AND TRANSACTION_TYPE = '")
                            .append(rule.getArguments().get(1))
                            .append("' HAVING savings ")
                            .append(rule.getArguments().get(2))
                            .append(rule.getArguments().get(3));
            case TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW ->
                    result.append("SELECT * FROM ( (SELECT SUM(TRANSACTION_AMOUNT) deposits FROM CTE WHERE PRODUCT_TYPE = '")
                            .append(rule.getArguments().get(0))
                            .append("' AND TRANSACTION_TYPE = 'DEPOSIT' ) JOIN (")
                            .append("SELECT SUM(TRANSACTION_AMOUNT) withdraws FROM CTE WHERE PRODUCT_TYPE ='")
                            .append(rule.getArguments().get(0))
                            .append("' AND TRANSACTION_TYPE = 'WITHDRAW' ) )")
                            .append("WHERE deposits ")
                            .append(rule.getArguments().get(1))
                            .append(" withdraws");
        }
        result.append(") THEN ").append(rule.getId()).append(" END ");
        return result.toString();
    }
    public UserRecommendationsResponse getUserRecommendations(UUID userId) {
        Collection<RecommendationRule> rules = rulesService.getAllRules();
        jdbcTemplate.execute("""
                DROP TABLE IF EXISTS CTE;
                CREATE TEMPORARY TABLE CTE NOT PERSISTENT AS
                SELECT t.TYPE TRANSACTION_TYPE, t.AMOUNT TRANSACTION_AMOUNT, p.TYPE PRODUCT_TYPE, p.NAME PRODUCT_NAME
                FROM TRANSACTIONS t JOIN PRODUCTS p ON t.PRODUCT_ID=p.ID where USER_ID='
                """ + userId + "';");
        StringBuilder sql = new StringBuilder("WITH rules_cte AS(SELECT C1 as TRUE_RULE FROM(");
        boolean isFirst = true;
        for (RecommendationRule rule : rules) {
            if (!isFirst) {
                sql.append(" UNION ");
            } else {
                isFirst = false;
            }
            if (Objects.isNull(rule.getAlternativeRuleId())) {
                sql.append(createSqlQueryFromRule(rule));
            } else {
                long alternativeRuleId = rule.getAlternativeRuleId();
                RecommendationRule alternativeRule = rules.stream().filter(
                        (r) -> r.getId() == alternativeRuleId).findAny().orElseThrow(
                                () -> new EntityNotFoundException(
                                        "This error should not exist. Alternative rule not found. Rule ID = "+rule.getId()+
                                                ", alternative rule ID = "+alternativeRuleId));
                sql.append("SELECT CASE WHEN EXISTS (");
                sql.append(createSqlQueryFromRule(rule));
                sql.append(" UNION ");
                sql.append(createSqlQueryFromRule(alternativeRule));
                sql.append(") THEN ").append(rule.getId()).append(" END ");
            }
        }
        sql.append(")) SELECT * FROM RECOMMENDATIONS r WHERE r.ID NOT IN (SELECT DISTINCT RECOMMENDATION_ID FROM RECOMMENDATIONS_RULES LEFT JOIN rules_cte ON rules_id=true_rule WHERE true_rule IS NULL);");
        logger.warn(sql.toString());
        var result = new UserRecommendationsResponse();
        result.setUserId(userId);
        result.setRecommendationList(jdbcTemplate.query(sql.toString(), new RecommendationMapper()));
        return result;
    }

    public void deleteRecommendation(long id) {
        recommendationRepository.deleteById(id);
    }

    public GetAllRecommendationsResponse getAllRecommendations() {
        return new GetAllRecommendationsResponse(recommendationRepository.findAll());
    }

    public Recommendation addRecommendation(Recommendation recommendation) {
        return recommendationRepository.save(recommendation);
    }

    public Recommendation addRuleToRecommendations(Long recommendationId, RecommendationRule rule) {
        rule = rulesService.create(rule);
        Recommendation recommendation = recommendationRepository.findById(recommendationId).orElseThrow(() -> new EntityNotFoundException(recommendationId.toString()));
        if (recommendation.getRules().contains(rule)) {
            logger.warn("Recommendation rules already contains rule {}", rule);
            return recommendation;
        }
        recommendation.addRule(rule);
        recommendationRepository.save(recommendation);
        logger.info("To recommendation {} added rule {}", recommendationId, rule);
        return recommendation;
    }

    public Recommendation removeRuleFromRecommendations(Long recommendationId, Long ruleId) {
        RecommendationRule rule = rulesService.getRule(ruleId);
        Recommendation recommendation = recommendationRepository.findById(recommendationId).orElseThrow(() -> new EntityNotFoundException(recommendationId.toString()));
        if (!recommendation.getRules().contains(rule)) {
            logger.warn("Recommendation {} doesn't have rule {}", recommendationId, ruleId);
        } else if (recommendation.getRules().remove(rule)) {
            recommendationRepository.save(recommendation);
            logger.info("From recommendation {} removed rule {}", recommendationId, ruleId);
        } else {
            logger.warn("Cannot delete rule {} from recommendation {}. Something went wrong.", ruleId, recommendationId);
        }
        return recommendation;
    }
}
