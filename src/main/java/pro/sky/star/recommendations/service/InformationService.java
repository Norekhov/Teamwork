package pro.sky.star.recommendations.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Component;
import pro.sky.star.recommendations.model.InformationModel;

@Component
public class InformationService {

    @Autowired
    private BuildProperties buildProperties;

    public InformationModel getInfo() {
        return new InformationModel(buildProperties.getName(), buildProperties.getVersion());
    }
}
