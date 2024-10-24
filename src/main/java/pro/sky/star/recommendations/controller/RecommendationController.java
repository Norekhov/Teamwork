package pro.sky.star.recommendations.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.star.recommendations.model.GetAllRecommendationsResponse;
import pro.sky.star.recommendations.model.Recommendation;
import pro.sky.star.recommendations.model.RecommendationRule;
import pro.sky.star.recommendations.model.UserRecommendationsResponse;
import pro.sky.star.recommendations.service.RecommendationService;

import java.util.UUID;

@RestController
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @Operation(summary = "Return some recommendations by user-id")
    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserRecommendationsResponse> getRecommendations(UUID userId) {
        return ResponseEntity.ok(recommendationService.getUserRecommendations(userId));
    }

    @Operation(summary = "Add recommendation")
    @PostMapping()
    public ResponseEntity<Recommendation> addRecommendation(Recommendation recommendation) {
        return ResponseEntity.ok(recommendationService.addRecommendation(recommendation));
    }

    @Operation(summary = "Remove recommendation by UUID")
    @DeleteMapping()
    public ResponseEntity<String> deleteRecommendation(long id) {
        recommendationService.deleteRecommendation(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Show all recommendations")
    @GetMapping()
    public ResponseEntity<GetAllRecommendationsResponse> getAllRecommendations() {
        return ResponseEntity.ok(recommendationService.getAllRecommendations());
    }

    @Operation(summary = "Add rule to recommendation")
    @PostMapping("/{recommendationId}/rule")
    public ResponseEntity<Recommendation> addRuleToRecommendations(@PathVariable long recommendationId, @RequestBody RecommendationRule rule) {
        return ResponseEntity.ok(recommendationService.addRuleToRecommendations(recommendationId, rule));
    }

    @Operation(summary = "Remove rule from recommendation")
    @DeleteMapping("/{recommendationId}/rule")
    public ResponseEntity<Recommendation> removeRuleFromRecommendations(@PathVariable long recommendationId, @RequestParam long ruleId) {
        return ResponseEntity.ok(recommendationService.removeRuleFromRecommendations(recommendationId, ruleId));
    }
}
