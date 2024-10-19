package pro.sky.star.recommendations.model;

import java.util.List;
import java.util.Objects;

public class UserRecommendationsResponse {
    private String userId;
    private List<Recommendation> recommendationList;

    public UserRecommendationsResponse(String userId, List<Recommendation> recommendationList) {
        this.userId = userId;
        this.recommendationList = recommendationList;
    }

    public UserRecommendationsResponse() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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
        UserRecommendationsResponse userRecommendationsResponse = (UserRecommendationsResponse) o;
        return Objects.equals(userId, userRecommendationsResponse.userId) && Objects.equals(recommendationList, userRecommendationsResponse.recommendationList);
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
