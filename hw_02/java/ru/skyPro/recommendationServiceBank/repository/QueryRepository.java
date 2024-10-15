package ru.skyPro.recommendationServiceBank.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.skyPro.recommendationServiceBank.model.rules.Query;
import ru.skyPro.recommendationServiceBank.model.rules.Rule;

public interface QueryRepository extends ListCrudRepository<Query, Long> {
}
