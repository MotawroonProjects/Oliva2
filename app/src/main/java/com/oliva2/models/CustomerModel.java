package com.oliva2.models;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.oliva2.tags.Tags;

import java.io.Serializable;

@Entity(tableName = Tags.table_customer,
        foreignKeys = {
                @ForeignKey(entity = CustomerGroupModel.class, parentColumns = "id", childColumns = "customer_group_id", onDelete = CASCADE)

        }

)
public class CustomerModel implements Serializable {
    @PrimaryKey
    private int id;
    private int customer_group_id;
    @Ignore
    private int user_id;
    private String name;
    @Ignore
    private String company_name;
    @Ignore
    private String email;
    @Ignore
    private String phone_number;
    @Ignore
    private String tax_no;
    @Ignore
    private String address;
    @Ignore
    private String city;
    @Ignore
    private String state;
    @Ignore
    private String postal_code;
    @Ignore
    private String country;
    @Ignore
    private String deposit;
    @Ignore
    private String expense;
    @Ignore
    private String created_at;
    @Ignore
    private String updated_at;

    public CustomerModel() {
    }

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

    public void setId(int id) {
        this.id = id;
    }

    public void setCustomer_group_id(int customer_group_id) {
        this.customer_group_id = customer_group_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setTax_no(String tax_no) {
        this.tax_no = tax_no;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
