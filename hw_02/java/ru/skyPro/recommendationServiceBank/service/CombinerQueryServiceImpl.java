package ru.skyPro.recommendationServiceBank.service;

import org.springframework.stereotype.Service;
import ru.skyPro.recommendationServiceBank.model.rules.Query;
import ru.skyPro.recommendationServiceBank.model.rules.Rule;
import ru.skyPro.recommendationServiceBank.repository.QueryRepository;
import ru.skyPro.recommendationServiceBank.repository.RuleRepository;

import java.util.Arrays;
import java.util.Objects;

@Service
public class CombinerQueryServiceImpl implements CombinerQueryService {
    private final String DEBIT = "DEBIT";
    private final String CREDIT = "CREDIT";
    private final String INVEST = "INVEST";
    private final String SAVING = "SAVING";
    private final String USER = "USER_OF";
    private final String ACTIVE = "ACTIVE_USER_OF";
    private final String TRANSACTION_SUM = "TRANSACTION_SUM_COMPARE";
    private final String TRANSACTION_SUM_COMPARE = "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW";
    private final RuleRepository ruleRepository;
    private final QueryRepository queryRepository;

    public CombinerQueryServiceImpl(RuleRepository ruleRepository, QueryRepository queryRepository) {
        this.ruleRepository = ruleRepository;
        this.queryRepository = queryRepository;
    }

    public Query saveQuery(Rule rule) {
        return switch (rule.getQueryName()) {
            case USER -> queryRepository.save(new Query(setQueryUserOfProduct(DEBIT)));
            case ACTIVE -> queryRepository.save(new Query(setQueryActiveUserOfProduct(DEBIT)));
            case TRANSACTION_SUM -> queryRepository.
                    save(new Query(setQueryTransactionSummaryCompare(DEBIT, 1000)));
            case TRANSACTION_SUM_COMPARE -> queryRepository.
                    save(new Query(setQueryTransactionSumCompareDepositWithdraw(DEBIT)));
            default -> throw new IllegalStateException("Unexpected value: " + rule.getQueryName());
        };
    }

    @Override
    public String setQueryUserOfProduct(String product) {
        Rule rule = ruleRepository.findByQueryName(USER);
        return "p.type  " + (rule.isNegate()?"!=":"=") + Arrays.stream(rule.getArguments()).filter(p -> Objects.equals(p, product));
    }

    @Override
    public String setQueryActiveUserOfProduct(String product) {
        Rule rule = ruleRepository.findByQueryName(ACTIVE);
        return "p.type "  +  (rule.isNegate()?"!=":"=") + Arrays.stream(rule.getArguments()).filter(p -> Objects.equals(p, product)) + "having count(t.amount) >5 ";
    }

    @Override
    public String setQueryTransactionSummaryCompare(String debit, int limit) {
        Rule rule = ruleRepository.findByQueryName(TRANSACTION_SUM);
        return "SUM(CASE WHEN p.type " +
                (rule.isNegate()?"!=":"=") + Arrays.stream(rule.getArguments()).filter(p -> Objects.equals(p, debit)) + " > " + limit;
    }

    @Override
    public String setQueryTransactionSumCompareDepositWithdraw(String debit) {
        Rule rule = ruleRepository.findByQueryName(TRANSACTION_SUM_COMPARE);
        return "SUM(CASE WHEN p.type " +
                (rule.isNegate()?"!=":"=") + Arrays.stream(rule.getArguments()).filter(p -> Objects.equals(p, debit)) +
                "THEN t.amount ELSE 0 END) > SUM(CASE WHEN " +
                "p.type " + (rule.isNegate()?"!=":"=") + " WITHDRAW "+
                " THEN t.amount ELSE 0 END) " ;
    }
}
