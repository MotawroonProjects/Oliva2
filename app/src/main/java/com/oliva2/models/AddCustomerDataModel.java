package com.oliva2.models;

import android.content.Context;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

import com.oliva2.R;

import java.io.Serializable;

public class AddCustomerDataModel extends BaseObservable implements Serializable {
    private String customer_group_id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String city;

    public ObservableField<String> error_name = new ObservableField<>();
    public ObservableField<String> error_phone = new ObservableField<>();
    public ObservableField<String> error_address = new ObservableField<>();
    public ObservableField<String> error_city = new ObservableField<>();

    public AddCustomerDataModel() {
        customer_group_id ="";
        name="";
        email="";
        phone="";
        address="";
        city="";
    }

    public boolean isDataValid(Context context){
        if (!customer_group_id.isEmpty()&&!name.isEmpty()&&!phone.isEmpty()&&!address.isEmpty()&&!city.isEmpty()){
            error_name.set(null);
            error_phone.set(null);
            error_address.set(null);
            error_city.set(null);

            return true;
        }else {

            if (name.isEmpty()){
                error_name.set(context.getString(R.string.field_req));

            }else {
                error_name.set(null);

            }
            if (phone.isEmpty()){
                error_phone.set(context.getString(R.string.field_req));
            }
            else {
                error_phone.set(null);
            }
            if (address.isEmpty()){
                error_address.set(context.getString(R.string.field_req));
            }
            else {
                error_address.set(null);
            }
            if (city.isEmpty()){
                error_city.set(context.getString(R.string.field_req));
            }
            else {
                error_city.set(null);
            }
            if(customer_group_id.isEmpty()){
                Toast.makeText(context,context.getResources().getString(R.string.ch_customergroup),Toast.LENGTH_LONG).show();
            }
            return false;
        }
    }

    public String getCustomer_group_id() {
        return customer_group_id;
    }

    public void setCustomer_group_id(String customer_group_id) {
        this.customer_group_id = customer_group_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}