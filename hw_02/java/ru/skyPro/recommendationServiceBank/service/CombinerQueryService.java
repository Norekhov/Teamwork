package ru.skyPro.recommendationServiceBank.service;

public interface CombinerQueryService {
    String setQueryUserOfProduct(String product);
    String setQueryActiveUserOfProduct(String product);
    String setQueryTransactionSummaryCompare(String debit, int limit);
    String setQueryTransactionSumCompareDepositWithdraw(String debit);

}
