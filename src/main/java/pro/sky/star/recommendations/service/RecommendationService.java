package pro.sky.star.recommendations.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.star.recommendations.model.*;
import pro.sky.star.recommendations.recommendation.RecommendationForInvest500;
import pro.sky.star.recommendations.recommendation.RecommendationForSimpleLoan;
import pro.sky.star.recommendations.recommendation.RecommendationForTopSaving;
import pro.sky.star.recommendations.repository.RecommendationsRepository;
import pro.sky.star.recommendations.repository.RulesRepository;
import pro.sky.star.recommendations.repository.StatisticRepository;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Service
public class RecommendationService {
    private Logger logger = LoggerFactory.getLogger(RecommendationService.class);

    private final RecommendationsRepository recommendationsRepository;

    private final RulesRepository rulesRepository;

    private final StatisticRepository statisticRepository;

    private final RecommendationForInvest500 recommendationForInvest500;

    private final RecommendationForSimpleLoan recommendationForSimpleLoan;

    private final RecommendationForTopSaving recommendationForTopSaving;

    public RecommendationService(RecommendationsRepository recommendationsRepository, RulesRepository rulesRepository, StatisticRepository statisticRepository,
                                 RecommendationForInvest500 recommendationForInvest500,
                                 RecommendationForSimpleLoan recommendationForSimpleLoan,
                                 RecommendationForTopSaving recommendationRuleSetTopSaving) {
        this.recommendationsRepository = recommendationsRepository;
        this.rulesRepository = rulesRepository;
        this.statisticRepository = statisticRepository;
        this.recommendationForInvest500 = recommendationForInvest500;
        this.recommendationForSimpleLoan = recommendationForSimpleLoan;
        this.recommendationForTopSaving = recommendationRuleSetTopSaving;
    }


    public Model get(String id) {
        logger.info("Запрос рекомендаций для пользователя ID: {}", id);
        List<Recommendation> recommendationList = new ArrayList<>();

        if (!Objects.isNull(recommendationForInvest500.check(id))) {
            recommendationList.add(recommendationForInvest500.check(id));
            logger.info("Добавлена рекомендация \"Invest 500\"");
        }
        if (!Objects.isNull(recommendationForTopSaving.check(id))) {
            recommendationList.add(recommendationForTopSaving.check(id));
            logger.info("Добавлена рекомендация \"Top Saving\"");
        }
        if (!Objects.isNull(recommendationForSimpleLoan.check(id))) {
            recommendationList.add(recommendationForSimpleLoan.check(id));
            logger.info("Добавлена рекомендация \"Simple Credit\"");
        }


        List<Product> productList = rulesRepository.findAll();
        UUID uuid = UUID.fromString(id);
        logger.info("Получен список продуктов: {}", productList.size());


        for (Product product : productList) {
            for (int j = 0; j < product.getRule().size(); j++) {
                String queryType = product.getRule().get(j).getQuery();
                logger.debug("Проверка правила: {}", queryType);
                if (queryType.equals("USER_OF")) {
                    boolean negate = recommendationsRepository
                            .checkUserOfAndActiveUserOf(uuid, product.getRule().get(j).getArguments().get(0), "SUM");
                    product.getRule().get(j).setNegate(negate);
                    logger.debug("Правило USER_OF: {}", negate);
                }
                if (queryType.equals("ACTIVE_USER_OF")) {
                    boolean negate = recommendationsRepository
                            .checkUserOfAndActiveUserOf(uuid, product.getRule().get(j).getArguments().get(0), "COUNT");
                    product.getRule().get(j).setNegate(negate);
                    logger.debug("Правило ACTIVE_USER_OF: {}", negate);
                }
                if (queryType.equals("TRANSACTION_SUM_COMPARE")) {
                    boolean negate = recommendationsRepository
                            .checkTransactionSumCompare(uuid, product.getRule().get(j).getArguments());
                    product.getRule().get(j).setNegate(negate);
                    logger.debug("Правило TRANSACTION_SUM_COMPARE: {}", negate);
                }
                if (queryType.equals("TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW")) {
                    boolean negate = recommendationsRepository
                            .checkTransactionSumCompareDepositWithdraw(uuid, product.getRule().get(j).getArguments());
                    product.getRule().get(j).setNegate(negate);
                    logger.debug("Правило TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW: {}", negate);
                }
            }

            if (product.getRule()
                    .stream()
                    .allMatch(Query::isNegate)) {
                recommendationList.add(new Recommendation(
                        product.getProduct_name(),
                        product.getProduct_text(),
                        product.getProduct_id()
                ));
                statisticRepository.incrementCount(product.getProduct_id());
                logger.info("Добавлена рекомендация для продукта: {}", product.getProduct_name());
            }
        }
        return new Model(id, recommendationList);
    }


    private Product getProductJson() {
        List<Product> products = rulesRepository.findAll();
        logger.info("Получен список продуктов, размер: {}", products.size());
        return new Product(products);
    }

    private UUID generateUUIDFromString(String input) {
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        return UUID.nameUUIDFromBytes(bytes);
    }

    public UserDB getUserId(String userName) {
        return recommendationsRepository.getIdUser(userName);
    }

    public void clearCaches() {
        recommendationsRepository.clearCaches();
    }

    public StatisticDTO getStats() {
        return new StatisticDTO(statisticRepository.findAll());
    }
}
