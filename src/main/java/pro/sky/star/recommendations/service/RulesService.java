package pro.sky.star.recommendations.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.star.recommendations.model.Product;
import pro.sky.star.recommendations.model.Query;
import pro.sky.star.recommendations.repository.QueryRepository;
import pro.sky.star.recommendations.repository.RulesRepository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class RulesService {
    private static final Logger logger = LoggerFactory.getLogger(RulesService.class);
    private final RulesRepository rulesRepository;
    private final QueryRepository queryRepository;

    public RulesService(RulesRepository rulesRepository,
                        QueryRepository queryRepository) {
        this.rulesRepository = rulesRepository;
        this.queryRepository = queryRepository;
    }


    public Product createRulesRecommendations(Product product) {
        if (Objects.isNull(product)) {
            throw new IllegalArgumentException("Условия ошибочны");
        }
        if (product.getRule().isEmpty() || Objects.isNull(product.getRule())) {
            throw new IllegalArgumentException("Условия ошибочны");
        }
        List<Query> queryList = product.getRule();
        for (Query query : queryList) {
            queryRepository.save(query);
        }
        return rulesRepository.save(product);
    }

    public Collection<Query> getAllQueryRecommendations() {
        Collection<Query> products = queryRepository.findAll();
        return products;
    }

    public Collection<Product> getAllRulesRecommendations() {
        Collection<Product> products = rulesRepository.findAll();
        return products;
    }

    public void deleteRulesRecommendations(long id) {
        if (!rulesRepository.existsById(id)) {
            throw new RuntimeException("Ошибка! Рекомендация под id= %d не обнаружена".formatted(id));
        }
        rulesRepository.deleteById(id);
    }
}