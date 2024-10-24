package pro.sky.star.recommendations.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Table(name = "rules")
@Entity
public class RecommendationRule {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rules_seq")
    @SequenceGenerator(name = "rules_seq", allocationSize = 1)
    Long id;

    private RecommendationRuleType query;

    private List<String> arguments;

    boolean negate;

    public Long getAlternativeRuleId() {
        return alternativeRuleId;
    }

    public void setAlternativeRuleId(Long alternativeRuleId) {
        this.alternativeRuleId = alternativeRuleId;
    }

    Long alternativeRuleId;

    public RecommendationRule(RecommendationRuleType query, List<String> arguments, boolean negate) {
        this.query = query;
        this.arguments = arguments;
        this.negate = negate;
    }

    public RecommendationRule() {
    }

    public RecommendationRuleType getQuery() {
        return query;
    }

    public void setQuery(RecommendationRuleType query) {
        this.query = query;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public boolean isNegate() {
        return negate;
    }

    public void setNegate(boolean negate) {
        this.negate = negate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecommendationRule recommendationRule1 = (RecommendationRule) o;
        return negate == recommendationRule1.negate && Objects.equals(query, recommendationRule1.query) && Objects.equals(arguments, recommendationRule1.arguments) && Objects.equals(id, recommendationRule1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, arguments, negate);
    }

    @Override
    public String toString() {
        return "{" +
                "query='" + query + '\'' +
                ", arguments=" + arguments +
                ", negate=" + negate +
                ", id=" + id +
                '}';
    }
}
