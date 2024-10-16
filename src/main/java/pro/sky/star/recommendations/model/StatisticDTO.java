package pro.sky.star.recommendations.model;

import java.util.List;
import java.util.Objects;

public class StatisticDTO {

    private List<Statistic> stats;

    public StatisticDTO(List<Statistic> stats) {
        this.stats = stats;
    }

    public StatisticDTO() {
    }

    @Override
    public String toString() {
        return "StatisticDTO{" +
                "stats=" + stats +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatisticDTO that = (StatisticDTO) o;
        return Objects.equals(stats, that.stats);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(stats);
    }

    public List<Statistic> getStats() {
        return stats;
    }

    public void setStats(List<Statistic> stats) {
        this.stats = stats;
    }
}
