package ru.skyPro.norekhovAndHisCommand.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import ru.skyPro.norekhovAndHisCommand.model.Model;
import ru.skyPro.norekhovAndHisCommand.service.RecommendationService;

import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecommendationControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RecommendationController controller;

    @Test
    public void testGetRecommendationUser() {
        // Arrange
        Model model = recommendationService.get("cd515076-5d8a-44be-930e-8d4fcb79f42d");

        // Act
        ResponseEntity<Model> clientRecommendationeResponseEntity =
                restTemplate.getForEntity("http://localhost:" + port + "/" + model.getUserId(), Model.class);
        // Assert
        Assertions.assertEquals(clientRecommendationeResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        assertThat(clientRecommendationeResponseEntity.getBody()).isNotNull();
        assert (Objects.requireNonNull(clientRecommendationeResponseEntity.getBody()).getUserId().equals(model.getUserId()));
    }
}
