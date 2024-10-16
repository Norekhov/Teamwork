package pro.sky.star.recommendations.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.star.recommendations.model.InformationModel;
import pro.sky.star.recommendations.service.InformationService;

@RestController
public class InformationController {

    private final InformationService informationService;

    public InformationController(InformationService informationService) {
        this.informationService = informationService;
    }

    @GetMapping("/management/info")
    public InformationModel getInformation() {
        return informationService.getInfo();
    }
}
