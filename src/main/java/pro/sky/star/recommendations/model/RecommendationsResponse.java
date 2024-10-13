package pro.sky.star.recommendations.model;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class RecommendationsResponse {
    private UUID userId;
    private List<Recommendation> recommendationList;

    public RecommendationsResponse(UUID userId, List<Recommendation> recommendationList) {
        this.userId = userId;
        this.recommendationList = recommendationList;
    }

    public RecommendationsResponse() {
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public List<Recommendation> getRecommendationList() {
        return recommendationList;
    }

    public void setRecommendationList(List<Recommendation> recommendationList) {
        this.recommendationList = recommendationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecommendationsResponse recommendationsResponse = (RecommendationsResponse) o;
        return Objects.equals(userId, recommendationsResponse.userId) && Objects.equals(recommendationList, recommendationsResponse.recommendationList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, recommendationList);
    }

    @Override
    public String toString() {
        return "Model{" +
                "userId='" + userId + '\'' +
                ", recommendationList=" + recommendationList +
                '}';
    }
}
