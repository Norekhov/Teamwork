package pro.sky.star.recommendations.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
public class Recommendation {
    private String document;
    private String name;
    private UUID id;

    public Recommendation(String document, String name, UUID id) {
        this.document = document;
        this.name = name;
        this.id = id;
    }

    public Recommendation() {
    }

    @Override
    public String toString() {
        return "Recommendation{" +
                "document='" + document + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recommendation that = (Recommendation) o;
        return id == that.id && Objects.equals(document, that.document) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(document, name, id);
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
