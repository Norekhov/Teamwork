package ru.skyPro.serviceBank.service;

import org.springframework.stereotype.Service;
import ru.skyPro.serviceBank.repository.RecommendationsRepository;

import java.util.UUID;
@Service
public class RecommendationsService {
    private final RecommendationsRepository repository;

    public RecommendationsService(RecommendationsRepository repository) {
        this.repository = repository;
    }

    public int findRandomUser(UUID user) {
        return repository.getRandomTransactionAmount(user);
    }

    public int getRecommendedInvest500(UUID user) {
        return repository.getInvest500Recommendation(user);
    }

    public int getRecommendedSimpleCredit(UUID user) {
        return repository.getRecommendedSimpleCredit(user);
    }

    public int getRecommendedTopSavingService(UUID user) {
        return repository.getRecommendedTopSaving(user);
    }
}
