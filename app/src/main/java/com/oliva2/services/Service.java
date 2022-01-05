package com.oliva2.services;


import com.oliva2.models.BrandDataModel;
import com.oliva2.models.CategoryDataModel;
import com.oliva2.models.ClosingDataModel;
import com.oliva2.models.CreateOrderModel;
import com.oliva2.models.CustomerDataModel;
import com.oliva2.models.CustomerGroupDataModel;
import com.oliva2.models.InvoiceDataModel;
import com.oliva2.models.InvoiceModel;
import com.oliva2.models.ProductDataDamageModel;
import com.oliva2.models.ProductDataModel;
import com.oliva2.models.SingleCustomerDataModel;
import com.oliva2.models.SingleOrderDataModel;
import com.oliva2.models.StatusResponse;
import com.oliva2.models.TaxDataModel;
import com.oliva2.models.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Service {

    @FormUrlEncoded
    @POST("api/login")
    Call<UserModel> login(
            @Field("name") String name,
            @Field("password") String password

    );
    @GET("api/categories")
    Call<CategoryDataModel> getDepartments();
    @GET("api/brands")
    Call<BrandDataModel> getBrands();

    @GET("api/productsToOffline")
    Call<ProductDataModel> getmainproduct(
            @Query("user_id") String user_id
    );

    @GET("api/getProductDetails")
    Call<ProductDataModel> getProductDetials(
            @Query("product_id") String product_id,
            @Query("warehouse_id") String warehouse_id

    );
    @GET("api/getProductDetails")
    Call<ProductDataModel> getProductDetials(
            @Query("product_id") String product_id,
            @Query("warehouse_id") String warehouse_id,
            @Query("amount") String amount


    );
    @POST("api/storeOrder")
    Call<SingleOrderDataModel> createOrder(
            @Body CreateOrderModel model
    );
    @GET("api/taxes")
    Call<TaxDataModel> getTax(


    );
    @GET("api/customers")
    Call<CustomerDataModel> getCustomer(


    );
    @GET("api/customerGroups")
    Call<CustomerGroupDataModel> getCustomerGroup(


    );
    @FormUrlEncoded
    @POST("api/storeCustomer")
    Call<SingleCustomerDataModel> addCustomer(
            @Field("customer_group_id") String customer_group_id,
            @Field("user_id") String user_id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone_number") String phone_number,
            @Field("address") String address,
            @Field("city") String city,
            @Field("id") String id


    );
    @GET("api/getLatestSale")
    Call<InvoiceModel> getLatestSale(
            @Query("user_id") String user_id


    );
    @GET("api/getInv")
    Call<InvoiceDataModel> getInv(
            @Query("sale_id") String sale_id,

            @Query("user_id") String user_id


    );
    @GET("api/loadEndProducts")
    Call<ProductDataDamageModel> getProducts(
            @Query("warehouse_id") String warehouse_id


    );
    @GET("api/productDamage")
    Call<StatusResponse> damage(
            @Query("user_id") String user_id,
            @Query("warehouse_id") String warehouse_id



    );
    @GET("api/check-cash-register-availability")
    Call<StatusResponse> checkAvilabilty(
            @Query("user_id") String user_id,
            @Query("warehouse_id") String warehouse_id

    );
    @FormUrlEncoded
    @POST("api/cash-register/store")
    Call<StatusResponse> enterMony(
            @Field("user_id") String user_id,
            @Field("warehouse_id") String warehouse_id,
            @Field("cash_in_hand") String cash_in_hand


    );
    @GET("api/cash-register/showDetails")
    Call<ClosingDataModel> getClosing(
            @Query("user_id") String user_id,
            @Query("warehouse_id") String warehouse_id

    );
    @FormUrlEncoded
    @POST("api/cash-register/close")
    Call<StatusResponse> closing(
            @Field("cash_register_id") String cash_register_id



    );
}