package ru.skyPro.recommendationServiceBank.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Setter
@Getter
@NoArgsConstructor
@Table("RECOMMENDATIONS")
public class BankRecommendation {
    @Id
    private String id;
    private String productName;
    private String description;

    @PersistenceCreator
    public BankRecommendation(String id, String productName, String description) {
        this.id = id;
        this.productName = productName;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Клиенту рекомендуется: " + productName + " -  " + description;
    }
}
