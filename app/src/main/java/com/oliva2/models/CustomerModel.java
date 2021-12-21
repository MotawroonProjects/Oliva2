package com.oliva2.models;

import java.io.Serializable;

public class CustomerModel implements Serializable {

    private int id;
    private int customer_group_id;
    private int user_id;
    private String name;
    private String company_name;
    private String email;
    private String phone_number;
    private String tax_no;
    private String address;
    private String city;
    private String state;
    private String postal_code;
    private String country;
    private String deposit;
    private String expense;
    private String created_at;
    private String updated_at;

    public CustomerModel(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getCustomer_group_id() {
        return customer_group_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getTax_no() {
        return tax_no;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public String getCountry() {
        return country;
    }

    public String getDeposit() {
        return deposit;
    }

    public String getExpense() {
        return expense;
    }


    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
