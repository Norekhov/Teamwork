package ru.skyPro.serviceBank.exceptions;

public class RecommendBankException extends RuntimeException {
    public RecommendBankException() {
        super("That user does not have recommend bank");
    }
}
