package ru.skyPro.recommendationServiceBank.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.skyPro.recommendationServiceBank.model.rules.DynamicRule;

@Repository
public interface DynamicRuleRepository extends ListCrudRepository<DynamicRule, Integer> {

}
