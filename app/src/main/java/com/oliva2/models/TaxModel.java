package com.oliva2.models;

import java.io.Serializable;

public class TaxModel implements Serializable {

        private int id;
        private String name;
        private double rate;
        private int is_active;
        private String created_at;
        private String updated_at;

    public TaxModel(String name) {
        this.name = name;
    }

    public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public double getRate() {
            return rate;
        }

        public int getIs_active() {
            return is_active;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }


}
