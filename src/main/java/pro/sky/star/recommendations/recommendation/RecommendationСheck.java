package pro.sky.star.recommendations.recommendation;

import org.springframework.stereotype.Repository;
import pro.sky.star.recommendations.model.Recommendation;

@Repository
public interface RecommendationСheck {

    Recommendation check(String id);
}
