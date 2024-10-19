package pro.sky.star.recommendations.model;

import java.util.List;

public class GetAllRecommendationsResponse {
    private List<Recommendation> data;

    public GetAllRecommendationsResponse(List<Recommendation> data) {
        this.data = data;
    }

    public GetAllRecommendationsResponse() {
    }

    public List<Recommendation> getData() {
        return data;
    }
}