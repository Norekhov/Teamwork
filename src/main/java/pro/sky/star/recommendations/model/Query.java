package pro.sky.star.recommendations.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Objects;

@Table(name = "query_product")
@Entity
public class Query {
    private String query;

    private List<String> arguments;

    boolean negate;

    @Id
    @GeneratedValue
    Long id;

    public Query(String query, List<String> arguments, boolean negate) {
        this.query = query;
        this.arguments = arguments;
        this.negate = negate;
    }

    public Query() {
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
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
        Query query1 = (Query) o;
        return negate == query1.negate && Objects.equals(query, query1.query) && Objects.equals(arguments, query1.arguments) && Objects.equals(id, query1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, arguments, negate, id);
    }

    @Override
    public String toString() {
        return "Query{" +
                "query='" + query + '\'' +
                ", arguments=" + arguments +
                ", negate=" + negate +
                ", id=" + id +
                '}';
    }
}
