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
            case USER -> queryRepository.save(new Query(setQueryUserOfProduct()));
            case ACTIVE -> queryRepository.save(new Query(setQueryActiveUserOfProduct()));
            case TRANSACTION_SUM -> queryRepository.
                    save(new Query(setQueryTransactionSummaryCompare()));
            case TRANSACTION_SUM_COMPARE -> queryRepository.
                    save(new Query(setQueryTransactionSumCompareDepositWithdraw()));
            default -> throw new IllegalStateException("Unexpected value: " + rule.getQueryName());
        };
    }

    @Override
    public String setQueryUserOfProduct() {
        Rule rule = ruleRepository.findByQueryName(USER);
        return "p.type  " + (rule.isNegate() ? "!=" : "=") + rule.getArguments()[0];
    }

    @Override
    public String setQueryActiveUserOfProduct() {
        Rule rule = ruleRepository.findByQueryName(ACTIVE);
        return "SUM(CASE WHEN p.type " + (rule.isNegate() ? "!=" : "=") + rule.getArguments()[0] + "THEN 1 ELSE 0 END) > 5 ";
    }

    @Override
    public String setQueryTransactionSummaryCompare() {
        Rule rule = ruleRepository.findByQueryName(TRANSACTION_SUM);
        return "SUM(CASE WHEN p.type " +
                (rule.isNegate() ? "!=" : "=") + rule.getArguments()[0] + " THEN t.amount" + (rule.isNegate() ? "!=" : "=") + rule.getArguments()[1] + " ELSE 0 END) " + rule.getArguments()[2] + rule.getArguments()[3];
    }

    @Override
    public String setQueryTransactionSumCompareDepositWithdraw() {
        Rule rule = ruleRepository.findByQueryName(TRANSACTION_SUM_COMPARE);
        return "SUM(CASE WHEN p.type " +
                (rule.isNegate() ? "!=" : "=") + rule.getArguments()[0] +
                " THEN t.amount = 'DEPOSIT'  ELSE 0 END) " + rule.getArguments()[1] +
                " SUM(CASE WHEN p.type = WITHDRAW  THEN t.amount = 'WITHDRAW' ELSE 0 END) ";
    }
}
