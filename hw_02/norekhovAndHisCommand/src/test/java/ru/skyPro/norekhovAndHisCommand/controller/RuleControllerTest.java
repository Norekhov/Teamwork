package ru.skyPro.norekhovAndHisCommand.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import ru.skyPro.norekhovAndHisCommand.model.Product;
import ru.skyPro.norekhovAndHisCommand.model.Query;
import ru.skyPro.norekhovAndHisCommand.repository.QueryRepository;
import ru.skyPro.norekhovAndHisCommand.repository.RulesRepository;
import ru.skyPro.norekhovAndHisCommand.service.RulesService;

import java.util.*;

import static ru.skyPro.norekhovAndHisCommand.util.ConstantQueriesOperation.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RuleControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private RulesService rulesService;
    @Autowired
    private RulesRepository rulesRepository;
    @Autowired
    private QueryRepository queryRepository;

    private Product productOne;
    private Product productTwo;
    private Product productThree;
    private Query firstQuery;
    private Query secondQuery;
    private Query thirdQuery;
    private Query fourthQuery;
    private Query fifthQuery;
    private Query sixthQuery;
    private List<Query> firstQueries;
    private List<Query> secondQueries;
    private List<Query> thirdQueries;
    List<String> arguments1;
    List<String> arguments2;
    List<String> arguments3;
    List<String> arguments4;
    List<String> arguments5;


    @BeforeEach
    public void setUp() {
        arguments1 = Arrays.asList(DEBIT);
        arguments2 = Arrays.asList(INVEST);
        arguments3 = Arrays.asList(INVEST_ARGS);
        arguments4 = Arrays.asList(SAVING_ARGS);
        arguments5 = Arrays.asList(TRANSACTIONS_ARGS);
    }

    @Test
    public void createRulesTest() {
        // Arrange
        thirdQuery = new Query(TRANSACTION_SUM, arguments3, true);
        thirdQuery.setQuery(TRANSACTION_SUM_FOR_INVEST_QUERY);
        List<String> arguments6 = Arrays.asList(CREDIT_ARGS);

        sixthQuery = new Query(CREDIT, arguments6, true);
        sixthQuery.setQuery(USER_FOR_CREDIT_QUERY);

        fifthQuery = new Query(TRANSACTION_SUM_COMPARE, arguments5, true);
        fifthQuery.setQuery(TRANSACTION_SUM_COMPARE_QUERY);

        thirdQueries = List.of(sixthQuery, thirdQuery, fifthQuery);

        productThree = new Product(SIMPLE_CREDIT, SIMPLE_CREDIT_DESCRIPTION, thirdQueries);
        // Act
        ResponseEntity<Product> productResponseEntity = restTemplate.postForEntity("http://localhost:" + port + "/rule/", productThree, Product.class);
        // Assert
        Assertions.assertEquals(productResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        Assertions.assertNotNull(productResponseEntity.getBody());
    }

    @Test
    public void getAllRulesTest() {
        // Arrange
        firstQuery = new Query(USER, arguments1, true);
        firstQuery.setQuery(USER_QUERY);

        secondQuery = new Query(ACTIVE, arguments2, true);
        secondQuery.setQuery(USER_FOR_INVEST_QUERY);

        thirdQuery = new Query(TRANSACTION_SUM, arguments3, true);
        thirdQuery.setQuery(TRANSACTION_SUM_FOR_INVEST_QUERY);

        firstQueries = List.of(firstQuery, secondQuery, thirdQuery);

        fourthQuery = new Query(SAVING, arguments4, true);
        fourthQuery.setQuery(USER_FOR_SAVING);
        fifthQuery = new Query(TRANSACTION_SUM_COMPARE, arguments5, true);
        fifthQuery.setQuery(TRANSACTION_SUM_COMPARE_QUERY);

        secondQueries = List.of(firstQuery, fourthQuery, fifthQuery);

        productOne = new Product(INVEST500, NAME500, firstQueries);

        productTwo = new Product(TOP_SAVING, SIMPLE_CREDIT_DESCRIPTION, secondQueries);

        rulesService.createRulesRecommendations(productOne);
//        rulesService.createRulesRecommendations(productTwo);
        // Act
        ResponseEntity<List<Product>> productResponseEntity = restTemplate.getForEntity("http://localhost:" + port + "/rule/rules", null,
                new ParameterizedTypeReference<List<Product>>() {

                });
        // Assert
        Assertions.assertEquals(productResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        Assertions.assertNotNull(productResponseEntity);
    }

    @Test
    public void deleteRuleTest() {
        // Arrange: Create a product (rule) to delete
        firstQuery = new Query(USER, arguments1, true);
        firstQuery.setQuery(USER_QUERY);
        fourthQuery = new Query(SAVING, arguments4, true);
        fourthQuery.setQuery(USER_FOR_SAVING);
        fifthQuery = new Query(TRANSACTION_SUM_COMPARE, arguments5, true);
        fifthQuery.setQuery(TRANSACTION_SUM_COMPARE_QUERY);

        secondQueries = List.of(firstQuery, fourthQuery, fifthQuery);
        productTwo = new Product(TOP_SAVING, SIMPLE_CREDIT_DESCRIPTION, secondQueries);
        Product productToDelete = rulesService.createRulesRecommendations(productTwo); // Assuming createRulesRecommendations returns the saved Product
        int productId = Math.toIntExact(productToDelete.getId()); // Assuming there's a way to get the ID after creation

        // Act: Send DELETE request
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/rule/" + productId,
                HttpMethod.DELETE,
                null,
                Void.class
        );
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
    }
}

