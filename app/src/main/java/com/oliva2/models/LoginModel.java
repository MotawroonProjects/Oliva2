package com.oliva2.models;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;
import androidx.databinding.Bindable;

import com.oliva2.BR;
import com.oliva2.R;

import java.io.Serializable;

public class LoginModel extends BaseObservable implements Serializable {
    private String name;
    private String password;
    public ObservableField<String> error_password = new ObservableField<>();
    public ObservableField<String> error_name = new ObservableField<>();

    public LoginModel() {
        name ="";
        password="";
    }

    public boolean isDataValid(Context context){
        if (!name.isEmpty()&&!password.isEmpty()){
            error_name.set(null);
            error_password.set(null);

            return true;
        }else {

            if (name.isEmpty()){
                error_name.set(context.getString(R.string.field_req));

            }else {
                error_name.set(null);

            }
            if (password.isEmpty()){
                error_password.set(context.getString(R.string.field_req));
            }
            else {
                error_password.set(null);
            }

            return false;
        }
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);

    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }
}