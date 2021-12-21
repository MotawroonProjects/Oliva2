package com.oliva2.models;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

import com.oliva2.R;

import java.io.Serializable;

public class CashDataModel extends BaseObservable implements Serializable {
    private String paying_amount;
    private String paid_amount;
    private String payment_note;
    private String sale_note;
    private String staff_note;
    public ObservableField<String> error_paying_amount = new ObservableField<>();
    public ObservableField<String> error_paid_amount = new ObservableField<>();

    public CashDataModel() {
        paying_amount ="";
        paid_amount="";
        payment_note="";
        sale_note="";
        staff_note="";
    }

    public boolean isDataValid(Context context){
        if (!paying_amount.isEmpty()&&!paid_amount.isEmpty()){
            error_paid_amount.set(null);
            error_paying_amount.set(null);

            return true;
        }else {

            if (paying_amount.isEmpty()){
                error_paid_amount.set(context.getString(R.string.field_req));

            }else {
                error_paid_amount.set(null);

            }
            if (paid_amount.isEmpty()){
                error_paying_amount.set(context.getString(R.string.field_req));
            }
            else {
                error_paying_amount.set(null);
            }

            return false;
        }
    }

    public String getPaying_amount() {
        return paying_amount;
    }

    public void setPaying_amount(String paying_amount) {
        this.paying_amount = paying_amount;
    }

    public String getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(String paid_amount) {
        this.paid_amount = paid_amount;
    }

    public String getPayment_note() {
        return payment_note;
    }

    public void setPayment_note(String payment_note) {
        this.payment_note = payment_note;
    }

    public String getSale_note() {
        return sale_note;
    }

    public void setSale_note(String sale_note) {
        this.sale_note = sale_note;
    }

    public String getStaff_note() {
        return staff_note;
    }

    public void setStaff_note(String staff_note) {
        this.staff_note = staff_note;
    }
}