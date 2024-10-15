package ru.skyPro.recommendationServiceBank.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.skyPro.recommendationServiceBank.model.rules.Rule;

import java.util.List;

@Repository
public interface RuleRepository extends ListCrudRepository<Rule, Long> {
    Rule findByQueryName(String name);
}
