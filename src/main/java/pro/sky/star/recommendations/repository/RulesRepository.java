package pro.sky.star.recommendations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.star.recommendations.model.Product;

public interface RulesRepository extends JpaRepository<Product, Long> {
}
