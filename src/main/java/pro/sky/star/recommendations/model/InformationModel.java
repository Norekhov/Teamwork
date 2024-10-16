package pro.sky.star.recommendations.model;

public class InformationModel {

    private String name;
    private String versionService;

    public InformationModel(String name, String versionService) {
        this.name = name;
        this.versionService = versionService;
    }

    public InformationModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersionService() {
        return versionService;
    }

    public void setVersionService(String versionService) {
        this.versionService = versionService;
    }
}
