package com.oliva2.models;

import java.io.Serializable;
import java.util.List;

public class ItemCartModel implements Serializable {
    private String product_code;
    private int qty;
    private String product_batch_id;
    private int product_id;
    private String sale_unit;
    private double net_unit_price;
    private double discount;
    private List<Integer> products_id;
    private double tax_rate;
    private double tax;
    private double subtotal;
    private String name;
    private byte[] image;
    private int stock;


    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getProduct_batch_id() {
        return product_batch_id;
    }

    public void setProduct_batch_id(String product_batch_id) {
        this.product_batch_id = product_batch_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getSale_unit() {
        return sale_unit;
    }

    public void setSale_unit(String sale_unit) {
        this.sale_unit = sale_unit;
    }

    public double getNet_unit_price() {
        return net_unit_price;
    }

    public void setNet_unit_price(double net_unit_price) {
        this.net_unit_price = net_unit_price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public List<Integer> getProducts_id() {
        return products_id;
    }

    public void setProducts_id(List<Integer> products_id) {
        this.products_id = products_id;
    }

    public double getTax_rate() {
        return tax_rate;
    }

    public void setTax_rate(double tax_rate) {
        this.tax_rate = tax_rate;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
