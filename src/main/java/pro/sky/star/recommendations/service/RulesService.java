package pro.sky.star.recommendations.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.star.recommendations.model.Product;
import pro.sky.star.recommendations.model.Query;
import pro.sky.star.recommendations.repository.QueryRepository;
import pro.sky.star.recommendations.repository.RulesRepository;
import pro.sky.star.recommendations.repository.StatisticRepository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class RulesService {
    private static final Logger logger = LoggerFactory.getLogger(RulesService.class);
    private final StatisticRepository statisticRepository;
    private final RulesRepository rulesRepository;
    private final QueryRepository queryRepository;

    public RulesService(StatisticRepository statisticRepository, RulesRepository rulesRepository,
                        QueryRepository queryRepository) {
        this.statisticRepository = statisticRepository;
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
        logger.info("Создание правил рекомендаций для продукта: {}", product.getProduct_name());
        for (Query query : queryList) {
            queryRepository.save(query);
            logger.debug("Сохранено правило рекомендации: {}", query);
        }
        return rulesRepository.save(product);
    }

    public Collection<Query> getAllQueryRecommendations() {
        logger.info("Вызван метод для получения запросов рекомендаций.");
        Collection<Query> products = queryRepository.findAll();
        return products;
    }

    public Collection<Product> getAllRulesRecommendations() {
        logger.info("Вызван метод для получения правил рекомендаций.");
        Collection<Product> products = rulesRepository.findAll();
        logger.info("Получена коллекция правил рекомендаций, размер: {}", products.size());
        return products;
    }

    public void deleteRulesRecommendations(long id) {
        logger.info("Вызван метод для удаления правила рекомендации - id: {}", id);
        if (!rulesRepository.existsById(id)) {
            logger.error("Правило рекомендации - id = {}, не найдено", id);
            throw new RuntimeException("Ошибка! Рекомендация под id= %d не обнаружена".formatted(id));
        }
        statisticRepository.deleteById(rulesRepository.findById(id).get().getProduct_id());
        rulesRepository.deleteById(id);
        logger.info("Удалено правило рекомендации - id: {}", id);
    }
}