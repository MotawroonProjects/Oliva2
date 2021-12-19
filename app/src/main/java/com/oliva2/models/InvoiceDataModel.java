package com.oliva2.models;

import java.io.Serializable;
import java.util.List;

public class InvoiceDataModel extends StatusResponse implements Serializable {
    private LimsSaleData lims_sale_data;
    private List<LimsProductSaleData> lims_product_sale_data;
    private LimsWarehouseData lims_warehouse_data;
    private LimsCustomerData lims_customer_data;

    public LimsSaleData getLims_sale_data() {
        return lims_sale_data;
    }

    public List<LimsProductSaleData> getLims_product_sale_data() {
        return lims_product_sale_data;
    }

    public LimsWarehouseData getLims_warehouse_data() {
        return lims_warehouse_data;
    }

    public LimsCustomerData getLims_customer_data() {
        return lims_customer_data;
    }

    public class LimsSaleData implements Serializable{
        private int id;
        private int branch_id;
        private String reference_no;
        private int user_id;
        private int cash_register_id;
        private int customer_id;
        private int warehouse_id;
        private int biller_id;
        private int item;
        private int total_qty;
        private int total_discount;
        private double total_tax;
        private double total_price;
        private double grand_total;
        private int order_tax_rate;
        private int order_tax;
        private int order_discount;
        private String coupon_id;
        private int coupon_discount;
        private String shipping_cost;
        private int sale_status;
        private int payment_status;
        private String document;
        private int paid_amount;
        private String sale_note;
        private String staff_note;
        private String is_read;
        private String delivery_companies_id;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public int getBranch_id() {
            return branch_id;
        }

        public String getReference_no() {
            return reference_no;
        }

        public int getUser_id() {
            return user_id;
        }

        public int getCash_register_id() {
            return cash_register_id;
        }

        public int getCustomer_id() {
            return customer_id;
        }

        public int getWarehouse_id() {
            return warehouse_id;
        }

        public int getBiller_id() {
            return biller_id;
        }

        public int getItem() {
            return item;
        }

        public int getTotal_qty() {
            return total_qty;
        }

        public int getTotal_discount() {
            return total_discount;
        }

        public double getTotal_tax() {
            return total_tax;
        }

        public double getTotal_price() {
            return total_price;
        }

        public double getGrand_total() {
            return grand_total;
        }

        public int getOrder_tax_rate() {
            return order_tax_rate;
        }

        public int getOrder_tax() {
            return order_tax;
        }

        public int getOrder_discount() {
            return order_discount;
        }

        public String getCoupon_id() {
            return coupon_id;
        }

        public int getCoupon_discount() {
            return coupon_discount;
        }

        public String getShipping_cost() {
            return shipping_cost;
        }

        public int getSale_status() {
            return sale_status;
        }

        public int getPayment_status() {
            return payment_status;
        }

        public String getDocument() {
            return document;
        }

        public int getPaid_amount() {
            return paid_amount;
        }

        public String getSale_note() {
            return sale_note;
        }

        public String getStaff_note() {
            return staff_note;
        }

        public String getIs_read() {
            return is_read;
        }

        public String getDelivery_companies_id() {
            return delivery_companies_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }
    public class LimsProductSaleData implements Serializable{

        private int id;
        private int sale_id;
        private int product_id;
        private String product_batch_id;
        private String variant_id;
        private int qty;
        private int sale_unit_id;
        private double net_unit_price;
        private int discount;
        private int tax_rate;
        private double tax;
        private double total;
        private String created_at;
        private String updated_at;
        private ProductModel product;

        public int getId() {
            return id;
        }

        public int getSale_id() {
            return sale_id;
        }

        public int getProduct_id() {
            return product_id;
        }

        public String getProduct_batch_id() {
            return product_batch_id;
        }

        public String getVariant_id() {
            return variant_id;
        }

        public int getQty() {
            return qty;
        }

        public int getSale_unit_id() {
            return sale_unit_id;
        }

        public double getNet_unit_price() {
            return net_unit_price;
        }

        public int getDiscount() {
            return discount;
        }

        public int getTax_rate() {
            return tax_rate;
        }

        public double getTax() {
            return tax;
        }

        public double getTotal() {
            return total;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public ProductModel getProduct() {
            return product;
        }
    }
    public class LimsWarehouseData implements Serializable{
        private int id;
        private int branch_id;
        private String name;
        private String phone;
        private String email;
        private String address;
        private int is_active;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public int getBranch_id() {
            return branch_id;
        }

        public String getName() {
            return name;
        }

        public String getPhone() {
            return phone;
        }

        public String getEmail() {
            return email;
        }

        public String getAddress() {
            return address;
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

    public class LimsCustomerData implements Serializable{
        private int id;
        private int customer_group_id;
        private String user_id;
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
        private int is_active;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public int getCustomer_group_id() {
            return customer_group_id;
        }

        public String getUser_id() {
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

}
