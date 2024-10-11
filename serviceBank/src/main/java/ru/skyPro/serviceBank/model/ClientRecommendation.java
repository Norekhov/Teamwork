package ru.skyPro.serviceBank.model;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ClientRecommendation {
    private UUID id;
    private List<BankRecommendation> bankRecommendations;

    public ClientRecommendation(String id, List<BankRecommendation> recommendations) {
        this.id = UUID.fromString(id);
        this.bankRecommendations = recommendations;
    }
}
