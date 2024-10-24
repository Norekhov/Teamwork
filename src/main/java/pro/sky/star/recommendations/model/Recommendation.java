package pro.sky.star.recommendations.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "recommendations")
@Schema(accessMode = Schema.AccessMode.READ_ONLY)
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recommendations_seq")
    @SequenceGenerator(name = "recommendations_seq", allocationSize = 1)

    long id;
    private UUID productId;
    private String productName;
    private String productText;

    @ManyToMany
    private List<RecommendationRule> rules;

    public Recommendation(String productName, String productText, List<RecommendationRule> rules) {
        this.productName = productName;
        this.productId = UUID.randomUUID();
        this.productText = productText;
        this.rules = rules;
    }

    public Recommendation() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }

    public List<RecommendationRule> getRules() {
        return rules;
    }

    public void setRules(List<RecommendationRule> rules) {
        this.rules = rules;
    }
    public void addRule(RecommendationRule rule) {
        this.rules.add(rule);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recommendation recommendation = (Recommendation) o;
        return Objects.equals(id, recommendation.id) && Objects.equals(productName, recommendation.productName) && Objects.equals(productId, recommendation.productId) && Objects.equals(productText, recommendation.productText) && Objects.equals(rules, recommendation.rules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, productId, productText, rules);
    }

    @Override
    public String toString() {
        return "Recommendation{" +
               "id=" + id +
               ", product_name='" + productName + '\'' +
               ", product_id=" + productId +
               ", product_text='" + productText + '\'' +
               ", rule=" + rules +
               '}';
    }
}
