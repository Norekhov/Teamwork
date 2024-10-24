package pro.sky.star.recommendations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.star.recommendations.model.Recommendation;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

}
