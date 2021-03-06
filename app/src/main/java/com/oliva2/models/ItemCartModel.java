package com.oliva2.models;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.oliva2.tags.Tags;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = Tags.table_order_products,
        foreignKeys = {
                @ForeignKey(entity = CreateOrderModel.class, parentColumns = "localid", childColumns = "create_id", onDelete = CASCADE)

        }

)
public class ItemCartModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int localid;
    private int create_id;
    private String product_code;
    private int qty;
    private String product_batch_id;
    private int product_id;
    private String sale_unit;
    private double net_unit_price;
    private double discount;
    private String products_id;
    private double tax_rate;
    private double tax;
    private double subtotal;
    private String name;
    private byte[] image;
    private int stock;
    private int category_id;
    private String category_ids;

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

    public String getProducts_id() {
        return products_id;
    }

    public void setProducts_id(String products_id) {
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

    public int getCreate_id() {
        return create_id;
    }

    public void setCreate_id(int create_id) {
        this.create_id = create_id;
    }

    public int getLocalid() {
        return localid;
    }

    public void setLocalid(int localid) {
        this.localid = localid;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_ids() {
        return category_ids;
    }

    public void setCategory_ids(String category_ids) {
        this.category_ids = category_ids;
    }
}
