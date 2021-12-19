package com.oliva2.models;

import java.io.Serializable;
import java.util.List;

public class CreateOrderModel implements Serializable {
    private int user_id;
    private String reference_no;
    private String warehouse_id_hidden;
    private String warehouse_id;
    private String biller_id_hidden;
    private String biller_id;
    private String customer_id_hidden;
    private String customer_id;
    private String total_qty;
    private String total_discount;
    private double total_tax;
    private double total_price;
    private String item;
    private double order_tax;
    private double grand_total;
    private String coupon_discount;
    private String sale_status;
    private String coupon_active;
    private String coupon_id;
    private String pos;
    private String draft;
    private double paying_amount;
    private double paid_amount;
    private String delivery_companies_id;
    private String paid_by_id;
    private String gift_card_id;
    private String cheque_no;
    private String payment_note;
    private String sale_note;
    private String staff_note;
    private double order_discount;
    private double order_tax_rate;
    private String shipping_cost;
   private List<ItemCartModel> details;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getReference_no() {
        return reference_no;
    }

    public void setReference_no(String reference_no) {
        this.reference_no = reference_no;
    }

    public String getWarehouse_id_hidden() {
        return warehouse_id_hidden;
    }

    public void setWarehouse_id_hidden(String warehouse_id_hidden) {
        this.warehouse_id_hidden = warehouse_id_hidden;
    }

    public String getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(String warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    public String getBiller_id_hidden() {
        return biller_id_hidden;
    }

    public void setBiller_id_hidden(String biller_id_hidden) {
        this.biller_id_hidden = biller_id_hidden;
    }

    public String getBiller_id() {
        return biller_id;
    }

    public void setBiller_id(String biller_id) {
        this.biller_id = biller_id;
    }

    public String getCustomer_id_hidden() {
        return customer_id_hidden;
    }

    public void setCustomer_id_hidden(String customer_id_hidden) {
        this.customer_id_hidden = customer_id_hidden;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getTotal_qty() {
        return total_qty;
    }

    public void setTotal_qty(String total_qty) {
        this.total_qty = total_qty;
    }

    public String getTotal_discount() {
        return total_discount;
    }

    public void setTotal_discount(String total_discount) {
        this.total_discount = total_discount;
    }

    public double getTotal_tax() {
        return total_tax;
    }

    public void setTotal_tax(double total_tax) {
        this.total_tax = total_tax;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getOrder_tax() {
        return order_tax;
    }

    public void setOrder_tax(double order_tax) {
        this.order_tax = order_tax;
    }

    public double getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(double grand_total) {
        this.grand_total = grand_total;
    }

    public String getCoupon_discount() {
        return coupon_discount;
    }

    public void setCoupon_discount(String coupon_discount) {
        this.coupon_discount = coupon_discount;
    }

    public String getSale_status() {
        return sale_status;
    }

    public void setSale_status(String sale_status) {
        this.sale_status = sale_status;
    }

    public String getCoupon_active() {
        return coupon_active;
    }

    public void setCoupon_active(String coupon_active) {
        this.coupon_active = coupon_active;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getDraft() {
        return draft;
    }

    public void setDraft(String draft) {
        this.draft = draft;
    }

    public double getPaying_amount() {
        return paying_amount;
    }

    public void setPaying_amount(double paying_amount) {
        this.paying_amount = paying_amount;
    }

    public double getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(double paid_amount) {
        this.paid_amount = paid_amount;
    }

    public String getDelivery_companies_id() {
        return delivery_companies_id;
    }

    public void setDelivery_companies_id(String delivery_companies_id) {
        this.delivery_companies_id = delivery_companies_id;
    }

    public String getPaid_by_id() {
        return paid_by_id;
    }

    public void setPaid_by_id(String paid_by_id) {
        this.paid_by_id = paid_by_id;
    }

    public String getGift_card_id() {
        return gift_card_id;
    }

    public void setGift_card_id(String gift_card_id) {
        this.gift_card_id = gift_card_id;
    }

    public String getCheque_no() {
        return cheque_no;
    }

    public void setCheque_no(String cheque_no) {
        this.cheque_no = cheque_no;
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

    public double getOrder_discount() {
        return order_discount;
    }

    public void setOrder_discount(double order_discount) {
        this.order_discount = order_discount;
    }

    public double getOrder_tax_rate() {
        return order_tax_rate;
    }

    public void setOrder_tax_rate(double order_tax_rate) {
        this.order_tax_rate = order_tax_rate;
    }

    public String getShipping_cost() {
        return shipping_cost;
    }

    public void setShipping_cost(String shipping_cost) {
        this.shipping_cost = shipping_cost;
    }

    public List<ItemCartModel> getDetails() {
        return details;
    }

    public void setDetails(List<ItemCartModel> details) {
        this.details = details;
    }
}
