package pro.sky.star.recommendations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.star.recommendations.model.Query;

@Repository
public interface QueryRepository extends JpaRepository<Query, Long> {
}
