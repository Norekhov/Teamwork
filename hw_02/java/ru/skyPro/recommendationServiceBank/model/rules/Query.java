package ru.skyPro.recommendationServiceBank.model.rules;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("QUERIES")
public class Query {
    @Id
    private Long id;
    private String query;

    @PersistenceCreator
    public Query(String query) {
        this.query = query;
    }
}
