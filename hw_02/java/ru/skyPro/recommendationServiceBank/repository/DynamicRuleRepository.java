package ru.skyPro.recommendationServiceBank.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.skyPro.recommendationServiceBank.dto.DynamicRuleDto;

@Repository
public interface DynamicRuleRepository extends ListCrudRepository<DynamicRuleDto, Integer> {
     DynamicRuleDto findById(int id);
}
