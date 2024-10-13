package pro.sky.star.recommendations.recommendation;

import org.springframework.stereotype.Component;
import pro.sky.star.recommendations.model.Recommendation;
import pro.sky.star.recommendations.repository.RecommendationCheck;
import pro.sky.star.recommendations.repository.RecommendationsRepository;

import java.util.UUID;

@Component
public class RecommendationForInvest500 implements RecommendationCheck {
    RecommendationsRepository repository;

    public RecommendationForInvest500(RecommendationsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Recommendation check(UUID id) {
        Recommendation recommendation = null;
        if (checkSumProductTypeTransactionType(id, "DEBIT",
                "DEPOSIT") +
                checkSumProductTypeTransactionType(id, "DEBIT",
                        "WITHDRAW") > 0 &&
                repository.checkingRulesInDBWithoutProductType(id, "INVEST") > 0 &&
                checkSumProductTypeTransactionType(id, "SAVING",
                        "DEPOSIT") > 1_000
        ) recommendation = new Recommendation(
                """
                        Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка!
                        Воспользуйтесь налоговыми льготами и начните инвестировать с умом.
                        Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде.
                        Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями.
                        Откройте ИИС сегодня и станьте ближе к финансовой независимости!""",
                "Invest 500",
                1);
        return recommendation;
    }

    private int checkSumProductTypeTransactionType(UUID uuid, String productType, String transactionType) {
        return repository.checkingRulesForTransactionAmountForProductType(uuid, productType, transactionType);
    }
}
