package pro.sky.star.recommendations.model;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class Recommendation {
    public UUID id;
    public String product_name;
    public UUID product_id;
    public String product_text;
    public List<RecommendationRule> rule;

    public Recommendation(UUID id, String product_name, UUID product_id, String product_text, List<RecommendationRule> rule) {
        this.id = id;
        this.product_name = product_name;
        this.product_id = product_id;
        this.product_text = product_text;
        this.rule = rule;
    }

    public Recommendation() {
    }

    public Recommendation(String product_text, String product_name, int i) {
        this.product_name = product_name;
        this.product_id = UUID.randomUUID();
        this.product_text = product_text;
    }

    @Override
    public String toString() {
        return "{\"id\": " + id + ", \"product_name\": " + product_name +
                ", \"product_id\": " + product_id + ", \"product_name\": " + product_name +
                ", \"product_text\": " + product_text + ", \"rule\": " + rule + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recommendation that = (Recommendation) o;
        return Objects.equals(id, that.id) && Objects.equals(product_name, that.product_name) && Objects.equals(product_id, that.product_id) && Objects.equals(product_text, that.product_text) && Objects.equals(rule, that.rule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product_name, product_id, product_text, rule);
    }
}
