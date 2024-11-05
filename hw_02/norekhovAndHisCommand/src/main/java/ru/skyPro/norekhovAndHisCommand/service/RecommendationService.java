package ru.skyPro.norekhovAndHisCommand.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skyPro.norekhovAndHisCommand.model.Model;
import ru.skyPro.norekhovAndHisCommand.model.Recommendation;
import ru.skyPro.norekhovAndHisCommand.recommendation.RecommendationForInvest500;
import ru.skyPro.norekhovAndHisCommand.recommendation.RecommendationForSimpleLoan;
import ru.skyPro.norekhovAndHisCommand.recommendation.RecommendationForTopSaving;
import ru.skyPro.norekhovAndHisCommand.repository.RecommendationsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationService {
    private Logger logger = LoggerFactory.getLogger(RecommendationService.class);

    private final RecommendationsRepository recommendationsRepository;

    private final RecommendationForInvest500 recommendationForInvest500;

    private final RecommendationForSimpleLoan recommendationForSimpleLoan;

    private final RecommendationForTopSaving recommendationForTopSaving;

    public RecommendationService(RecommendationsRepository recommendationsRepository,
                                 RecommendationForInvest500 recommendationForInvest500,
                                 RecommendationForSimpleLoan recommendationForSimpleLoan,
                                 RecommendationForTopSaving recommendationRuleSetTopSaving) {
        this.recommendationsRepository = recommendationsRepository;
        this.recommendationForInvest500 = recommendationForInvest500;
        this.recommendationForSimpleLoan = recommendationForSimpleLoan;
        this.recommendationForTopSaving = recommendationRuleSetTopSaving;
    }

    public Model get(String id) {
        List<Recommendation> recommendationList = new ArrayList<>();
        recommendationList.add(recommendationForInvest500.check(id));
        recommendationList.add(recommendationForTopSaving.check(id));
        recommendationList.add(recommendationForSimpleLoan.check(id));
        return new Model(id, recommendationList);
    }

}
