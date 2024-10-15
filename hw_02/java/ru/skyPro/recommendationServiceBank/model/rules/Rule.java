package ru.skyPro.recommendationServiceBank.model.rules;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table("RULES")
public class Rule {
    @Id
    private Long id;
    private String queryName;
    private String[] arguments;
    private boolean negate;
    @MappedCollection(idColumn = "QUERY_ID")
    private Query query;

    @PersistenceCreator
    public Rule(Long id, String name, String[] arguments, boolean negate, Query query) {
        this.id = id;
        this.queryName = name;
        this.arguments = arguments;
        this.negate = negate;
        this.query = query;
    }
}
