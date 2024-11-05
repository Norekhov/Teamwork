package ru.skyPro.norekhovAndHisCommand.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
@AllArgsConstructor
@Table(name = "query_product")
@Entity
public class Query {
    @Id
    @GeneratedValue
    Long id;

    @Column(name="name")
    private String queryName;

    @Column(name = "arguments")
    @ElementCollection
    private List<String> arguments;

    @Column(name="negate")
    boolean negate;

    @Column(name="query")
    private String query;

//   @ManyToOne(cascade = CascadeType.ALL)
//   @JoinColumn(name="recomnend_is")
//   private Product product;

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

    public List<String>getArguments() {
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

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public String getQueryName() {
        return queryName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Query query1 = (Query) o;
        return negate == query1.negate && Objects.equals(query, query1.query) && Objects.equals(arguments, query1.arguments) && Objects.equals(id, query1.id);
    }

//    public void setProduct(Product product) {
//        this.product = product;
//    }
//
//    public Product getProduct() {
//        return product;
//    }

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
