package com.oliva2.models;

import java.io.Serializable;
import java.util.List;

public class ProductDetialsModel implements Serializable {
private     List<ProductModel> productModelList;
private int selection;
    public List<ProductModel> getProductModelList() {
        return productModelList;
    }

    public void setProductModelList(List<ProductModel> productModelList) {
        this.productModelList = productModelList;
    }

    public int getSelection() {
        return selection;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }
}
