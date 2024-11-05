package ru.skyPro.norekhovAndHisCommand.recommendation;

import org.springframework.stereotype.Component;
import ru.skyPro.norekhovAndHisCommand.model.Recommendation;
import ru.skyPro.norekhovAndHisCommand.repository.RecommendationsRepository;
import ru.skyPro.norekhovAndHisCommand.repository.RecommendationСheck;


import java.util.UUID;

@Component
public class RecommendationForInvest500 implements RecommendationСheck {
    RecommendationsRepository repository;

    public RecommendationForInvest500(RecommendationsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Recommendation check(String id) {
        UUID uuid = UUID.fromString(id);
        Recommendation recommendation = null;
        if (checkSumProductTypeTransactionType(uuid, "DEBIT",
                "DEPOSIT") +
                checkSumProductTypeTransactionType(uuid, "DEBIT",
                        "WITHDRAW") > 0 &&
                repository.checkingRulesInDBWithoutProductType(uuid, "INVEST") > 0 &&
                checkSumProductTypeTransactionType(uuid, "SAVING",
                        "DEPOSIT") > 1_000
        ) {
            recommendation = new Recommendation(
                    """
                            Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка!
                            Воспользуйтесь налоговыми льготами и начните инвестировать с умом.
                            Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде.
                            Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями.
                            Откройте ИИС сегодня и станьте ближе к финансовой независимости!""",
                    "Invest 500",
                    1);
        }
        return recommendation;
    }

    private int checkSumProductTypeTransactionType(UUID uuid, String productType, String transactionType) {
        return repository.checkingRulesForTransactionAmountForProductType(uuid, productType, transactionType);
    }
}
