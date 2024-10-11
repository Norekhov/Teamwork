package pro.sky.star.recommendations.model;


import java.util.List;
import java.util.UUID;

public class RecommendationsResponseDTO {
    public UUID user_id;
    public List<Recommendation> recommendations;
}
