package ru.skyPro.norekhovAndHisCommand.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skyPro.norekhovAndHisCommand.model.Query;


@Repository
public interface QueryRepository extends JpaRepository<Query, Long> {
    Query findByQueryName(String name);
}
