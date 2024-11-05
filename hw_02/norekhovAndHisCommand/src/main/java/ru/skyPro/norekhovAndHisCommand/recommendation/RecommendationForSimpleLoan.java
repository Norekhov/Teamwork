package ru.skyPro.norekhovAndHisCommand.recommendation;

import org.springframework.stereotype.Component;
import ru.skyPro.norekhovAndHisCommand.model.Recommendation;
import ru.skyPro.norekhovAndHisCommand.repository.RecommendationsRepository;
import ru.skyPro.norekhovAndHisCommand.repository.RecommendationСheck;

import java.util.UUID;

@Component
public class RecommendationForSimpleLoan implements RecommendationСheck {

    RecommendationsRepository repository;

    public RecommendationForSimpleLoan(RecommendationsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Recommendation check(String id) {
        UUID uuid = UUID.fromString(id);
        Recommendation recommendation = null;
        if (repository.checkingRulesInDBWithoutProductType(uuid, "CREDIT") > 0 &&
                checkSumProductTypeTransactionType(uuid, "DEBIT",
                        "WITHDRAW") > 100_000 &&
                checkSumProductTypeTransactionType(uuid, "DEBIT",
                        "DEPOSIT") >
                        checkSumProductTypeTransactionType(uuid, "DEBIT",
                                "WITHDRAW")
        ) {
            recommendation = new Recommendation(
                    """
                            Откройте мир выгодных кредитов с нами!
                            Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту.
                            Почему выбирают нас:
                            Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов.
                            Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении.
                            Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое.
                            Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!""",
                    "Простой кредит",
                    3);
        }
        return recommendation;
    }

    private int checkSumProductTypeTransactionType(UUID uuid, String productType, String transactionType) {
        return repository.checkingRulesForTransactionAmountForProductType(uuid, productType, transactionType);
    }
}
