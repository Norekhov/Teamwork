package pro.sky.star.recommendations.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.star.recommendations.model.Recommendation;
import pro.sky.star.recommendations.model.RecommendationsResponse;
import pro.sky.star.recommendations.service.RecommendationService;

import java.util.UUID;

@RequestMapping("/recommendation")
@RestController
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @Operation(summary = "Return some recommendations by user-id")
    @GetMapping(value = "/{userId}")
    public ResponseEntity<RecommendationsResponse> getRecommendations(UUID userId) {
        return ResponseEntity.ok(recommendationService.getRecommendations(userId));
    }

    @Operation(summary = "Add recommendation")
    @PostMapping()
    public ResponseEntity<Recommendation> addRecommendation(Recommendation recommendation) {
        return ResponseEntity.ok(recommendationService.addRecommendation(recommendation));
    }

    @Operation(summary = "Remove recommendation by UUID")
    @DeleteMapping()
    public ResponseEntity<String> deleteRecommendation(UUID recommendation_id) {
        recommendationService.deleteRecommendation(recommendation_id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Show all recommendations")
    @GetMapping()
    public ResponseEntity<RecommendationsResponse> getAllRecommendations() {
        return ResponseEntity.ok(recommendationService.getAllRecommendations());
    }
}
