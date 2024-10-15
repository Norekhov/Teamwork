package ru.skyPro.recommendationServiceBank.model.rules;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import ru.skyPro.recommendationServiceBank.model.BankRecommendation;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Table("DYNAMICS_RULES")
public class DynamicRule {
    @Id
    private int id;
    @MappedCollection(idColumn = "RECOMMENDATION_ID")
    private BankRecommendation recommendation;

    @MappedCollection(idColumn = "QUERY_ID")
    private Set<Rule> rules;

    @PersistenceCreator
    public DynamicRule(int id, BankRecommendation recommendation, Set<Rule> rules) {
        this.id = id;
        this.recommendation = recommendation;
        this.rules = rules;
    }
}
