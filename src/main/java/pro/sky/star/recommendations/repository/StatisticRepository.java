package pro.sky.star.recommendations.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pro.sky.star.recommendations.model.Statistic;

import java.util.UUID;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, UUID> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO stats (rule_id, count) VALUES (:uuid, 1)", nativeQuery = true)
    void insertCount(@Param("uuid") UUID uuid);

    @Modifying
    @Transactional
    @Query(value = "UPDATE stats SET count = count + 1 WHERE rule_id = :uuid", nativeQuery = true)
    void incrementCount(@Param("uuid") UUID uuid);
}
