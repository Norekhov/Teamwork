package pro.sky.star.recommendations.recommendation;

import org.springframework.stereotype.Component;
import pro.sky.star.recommendations.model.Recommendation;
import pro.sky.star.recommendations.repository.RecommendationsRepository;
import pro.sky.star.recommendations.repository.RecommendationСheck;

import java.util.UUID;
@Component
public class RecommendationForTopSaving implements RecommendationСheck {

    RecommendationsRepository repository;

    public RecommendationForTopSaving(RecommendationsRepository repository) {
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
                checkSumProductTypeTransactionType(uuid, "DEBIT",
                        "DEPOSIT") >
                        checkSumProductTypeTransactionType(uuid, "DEBIT",
                                "WITHDRAW") &&
                (checkSumProductTypeTransactionType(uuid, "DEBIT",
                        "DEPOSIT") >= 50_000 ||
                        checkSumProductTypeTransactionType(uuid, "SAVING",
                                "DEPOSIT") > 50_000)
        ) {
            recommendation = new Recommendation(
                    """
                            Откройте свою собственную «Копилку» с нашим банком! «Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать деньги на важные цели. Больше никаких забытых чеков и потерянных квитанций — всё под контролем!
                            Преимущества «Копилки»:Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет автоматически переводить определенную сумму на ваш счет.                 
                            Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления и корректируйте стратегию при необходимости.          
                            Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним возможен только через мобильное приложение или интернет-банкинг.                     
                            Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!""",
                    "Top Saving",
                    2);
        }
        return recommendation;
    }

    private int checkSumProductTypeTransactionType(UUID uuid, String productType, String transactionType) {
        return repository.checkingRulesForTransactionAmountForProductType(uuid, productType, transactionType);
    }
}
