package pro.sky.star.recommendations.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "product")
public class Product {

    private String product_name;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID product_id;

    private String product_text;

    @ElementCollection
    private List<Query> rule;

    @Id
    @GeneratedValue
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Long id;

    public Product(String product_name, String productText, List<Query> rule) {
        this.product_name = product_name;
        this.product_id = generateUUIDFromString(product_name); // Генерация UUID на основе названия продукта
        this.product_text = productText;
        this.rule = rule;
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
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public UUID getProduct_id() {
        return product_id;
    }

    public void setProduct_id(UUID product_id) {
        this.product_id = product_id;
    }

    public String getProduct_text() {
        return product_text;
    }

    public void setProduct_text(String product_text) {
        this.product_text = product_text;
    }

    public List<Query> getRule() {
        return rule;
    }

    public void setRule(List<Query> rule) {
        this.rule = rule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(product_name, product.product_name) && Objects.equals(product_id, product.product_id) && Objects.equals(product_text, product.product_text) && Objects.equals(rule, product.rule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product_name, product_id, product_text, rule);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", product_name='" + product_name + '\'' +
                ", product_id=" + product_id +
                ", product_text='" + product_text + '\'' +
                ", rule=" + rule +
                '}';
    }
}
