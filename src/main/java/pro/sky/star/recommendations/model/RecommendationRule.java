package pro.sky.star.recommendations.model;

import java.util.List;
import java.util.UUID;

public class RecommendationRule {
    public UUID id;
    public String query;
    List<String> arguments;
    boolean negate;
}
