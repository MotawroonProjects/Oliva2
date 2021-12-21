package com.oliva2.models;

import java.io.Serializable;
import java.util.List;

public class ProductDataDamageModel extends StatusResponse implements Serializable {
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public class Data implements Serializable {
        private ProductModel product;
        private double qty;

        public ProductModel getProduct() {
            return product;
        }

        public double getQty() {
            return qty;
        }
    }
}
