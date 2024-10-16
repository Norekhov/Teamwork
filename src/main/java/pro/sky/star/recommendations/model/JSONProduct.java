package pro.sky.star.recommendations.model;

import java.util.List;

public class JSONProduct {

    private List<Product> data;

    public JSONProduct(List<Product> data) {
        this.data = data;
    }

    public JSONProduct() {
    }

    public List<Product> getData() {
        return data;
    }
}