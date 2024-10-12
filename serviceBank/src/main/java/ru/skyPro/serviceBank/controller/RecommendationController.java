package ru.skyPro.serviceBank.controller;

import org.springframework.web.bind.annotation.*;
import ru.skyPro.serviceBank.model.BankRecommendation;
import ru.skyPro.serviceBank.model.ClientRecommendation;
import ru.skyPro.serviceBank.service.RecommendationsService;
import ru.skyPro.serviceBank.exceptions.RecommendBankException;

import java.util.*;

@RestController
@RequestMapping("/user")
public class RecommendationController {

    public final RecommendationsService service;

    public RecommendationController(RecommendationsService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ClientRecommendation getRecommendations(@PathVariable("id") String id) {
        return service.getClientRecommendation(UUID.fromString(id));
    }
}
