package ru.skyPro.serviceBank.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class BankRecommendation {
    private String id;
    private String productName;
    private String description;

    @Override
    public String toString() {
        return "Клиенту рекомендуется: " + productName + " -  " + description;
    }
}
