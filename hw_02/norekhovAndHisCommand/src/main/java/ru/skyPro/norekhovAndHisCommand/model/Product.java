package ru.skyPro.norekhovAndHisCommand.model;

import jakarta.persistence.*;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name="name")
    private String productName;

    private UUID productId;

    @Lob
    @Column(name = "description")
    private String productText;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="query_id")
    private List<Query> rules;

    public Product(String productName, String productText, List<Query> rules) {
        this.productName = productName;
        this.productId = generateUUIDFromString(productName); // Генерация UUID на основе названия продукта
        this.productText = productText;
        this.rules = rules;
    }

    public Product() {
    }

    private UUID generateUUIDFromString(String input) {
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        return UUID.nameUUIDFromBytes(bytes);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct_name() {
        return productName;
    }

    public void setProduct_name(String product_name) {
        this.productName = product_name;
    }

    public UUID getProduct_id() {
        return productId;
    }

    public void setProduct_id(UUID product_id) {
        this.productId = product_id;
    }

    public String getProduct_text() {
        return productText;
    }

    public void setProduct_text(String product_text) {
        this.productText = product_text;
    }

    public List<Query> getRule() {
        return rules;
    }

    public void setRule(List<Query> rule) {
        this.rules = rule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(productName, product.productName) && Objects.equals(productId, product.productId) && Objects.equals(productText, product.productText) && Objects.equals(rules, product.rules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, productId, productText, rules);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", product_name='" + productName + '\'' +
                ", product_id=" + productId +
                ", product_text='" + productText + '\'' +
                ", rule=" + rules +
                '}';
    }
}
