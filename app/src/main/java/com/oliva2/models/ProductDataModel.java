package com.oliva2.models;

import java.io.Serializable;
import java.util.List;

public class ProductDataModel extends StatusResponse implements Serializable {
    private List<ProductModel> data;
    private List<ProductModel> subProducts;
    private ProductModel product;
    private String type;

    public List<ProductModel> getData() {
        return data;
    }

    public List<ProductModel> getSubProducts() {
        return subProducts;
    }

    public String getType() {
        return type;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setData(List<ProductModel> data) {
        this.data = data;
    }
}
