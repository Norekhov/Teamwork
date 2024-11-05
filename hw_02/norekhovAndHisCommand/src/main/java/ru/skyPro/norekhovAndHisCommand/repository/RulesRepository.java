package ru.skyPro.norekhovAndHisCommand.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.skyPro.norekhovAndHisCommand.model.Product;

public interface RulesRepository extends JpaRepository<Product, Long> {
    Product findByProductName(String name);
}
