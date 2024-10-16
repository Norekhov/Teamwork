package pro.sky.star.recommendations.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "statistic")
public class Statistic {

    @Id
    private UUID ruleId; // Уникальный идентификатор правила

    private Integer check;

    @Override
    public String toString() {
        return "Statistic{" +
                "ruleId=" + ruleId +
                ", check=" + check +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistic statistic = (Statistic) o;
        return Objects.equals(ruleId, statistic.ruleId) && Objects.equals(check, statistic.check);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ruleId, check);
    }

    public UUID getRuleId() {
        return ruleId;
    }

    public void setRuleId(UUID ruleId) {
        this.ruleId = ruleId;
    }

    public Integer getCheck() {
        return check;
    }

    public void setCheck(Integer check) {
        this.check = check;
    }

    public Statistic() {
    }

    public Statistic(UUID ruleId, Integer check) {
        this.ruleId = ruleId;
        this.check = check;
    }
}
