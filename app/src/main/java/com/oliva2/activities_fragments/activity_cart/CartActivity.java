package com.oliva2.activities_fragments.activity_cart;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.oliva2.R;
import com.oliva2.activities_fragments.activity_add_customer.AddCustomerActivity;
import com.oliva2.activities_fragments.activity_home.HomeActivity;
import com.oliva2.activities_fragments.activity_invoice.InvoiceActivity;
import com.oliva2.adapters.CartAdapter;
import com.oliva2.adapters.SpinnerCustomerAdapter;
import com.oliva2.adapters.SpinnerTaxAdapter;
import com.oliva2.databinding.ActivityCartBinding;
import com.oliva2.language.Language;
import com.oliva2.local_database.AccessDatabase;
import com.oliva2.local_database.DataBaseInterfaces;
import com.oliva2.models.CashDataModel;
import com.oliva2.models.CreateOrderModel;
import com.oliva2.models.CustomerDataModel;
import com.oliva2.models.CustomerModel;
import com.oliva2.models.ItemCartModel;
import com.oliva2.models.TaxDataModel;
import com.oliva2.models.SingleOrderDataModel;
import com.oliva2.models.TaxModel;
import com.oliva2.models.UserModel;
import com.oliva2.preferences.Preferences;
import com.oliva2.remote.Api;
import com.oliva2.share.Common;
import com.oliva2.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements DataBaseInterfaces.CustomerInterface, DataBaseInterfaces.MainTaXInterface, DataBaseInterfaces.OrderInsertInterface, DataBaseInterfaces.ProductOrderInsertInterface {
    private ActivityCartBinding binding;
    private String lang;
    private Preferences preferences;
    private UserModel userModel;
    private AccessDatabase accessDatabase;


    private List<ItemCartModel> list;
    private CreateOrderModel createOrderModel;
    private double total, tax;
    private CartAdapter adapter;
    private int qty;
    private CashDataModel cashDataModel;
    private List<TaxModel> taxModelList;
    private SpinnerTaxAdapter spinnerTaxAdapter;
    private List<CustomerModel> customerModelList;
    private SpinnerCustomerAdapter spinnerCustomerAdapter;
    private ProgressDialog dialog;
//    private ActivityResultLauncher<Intent> launcher;
//    private SelectedLocation selectedLocation;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart);
        initView();
    }


    private void initView() {
            dialog = Common.createProgressDialog(this, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        list = new ArrayList<>();
        cashDataModel = new CashDataModel();
        taxModelList = new ArrayList<>();
        customerModelList = new ArrayList<>();
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        accessDatabase = new AccessDatabase(this);

        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        createOrderModel = preferences.getcart_olivaData(this);
        adapter = new CartAdapter(list, this);
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        binding.progBar.setVisibility(View.GONE);
        spinnerTaxAdapter = new SpinnerTaxAdapter(taxModelList, this);
        binding.spinnerTax.setAdapter(spinnerTaxAdapter);
        spinnerCustomerAdapter = new SpinnerCustomerAdapter(customerModelList, this);
        binding.spinnerCustomer.setAdapter(spinnerCustomerAdapter);
        binding.spinnerTax.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int spinnerpos, long id) {
                //  Log.e("dkdkk",position+"");
                Log.e("sssseeee", spinnerpos + "");

                if (spinnerpos > 0) {
                    if (createOrderModel != null) {

                        createOrderModel.setOrder_tax_rate(taxModelList.get(spinnerpos).getRate());
                        createOrderModel.setOrder_tax((createOrderModel.getTotal_price() * createOrderModel.getOrder_tax_rate()) / 100);
                        createOrderModel.setGrand_total(total - ((total * createOrderModel.getOrder_discount()) / 100) + createOrderModel.getOrder_tax());
                        cashDataModel.setPaying_amount(Math.round(createOrderModel.getGrand_total()) + "");
                        cashDataModel.setPaid_amount(Math.round(createOrderModel.getGrand_total()) + "");
                        binding.tvTotal.setText(String.format("%.2f", createOrderModel.getGrand_total()));
                    }
                } else {
                    if (createOrderModel != null) {

                        createOrderModel.setOrder_tax(0);
                        createOrderModel.setOrder_tax_rate(0);
                    }
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.spinnerCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int spinnerpos, long id) {
                //  Log.e("dkdkk",position+"");

                if (createOrderModel != null) {

                    createOrderModel.setCustomer_id_hidden(customerModelList.get(spinnerpos).getId() + "");
                    createOrderModel.setCustomer_id(customerModelList.get(spinnerpos).getId() + "");
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recView.setAdapter(adapter);
        binding.flAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, AddCustomerActivity.class);
                startActivity(intent);
            }
        });
        binding.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.lltaxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSheet();
            }
        });
        binding.lldiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSheet2();
            }
        });
        binding.llship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSheet3();
            }
        });
        binding.flcash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createOrderModel.setReference_no(binding.editInvoicenum.getText().toString());
                createOrderModel.setPaid_by_id("1");
                createOrderModel.setSale_status("1");
                openSheet4();
            }
        });
        binding.flcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createOrderModel.setReference_no(binding.editInvoicenum.getText().toString());
                createOrderModel.setPaid_by_id("3");
                createOrderModel.setSale_status("1");
                openSheet4();
            }
        });
        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checktax();
            }
        });
        binding.btnConfirm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkdiscount();
            }
        });
        binding.btnConfirm3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSheet3();
            }
        });
        binding.btnConfirm4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });

        binding.flclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSheet();
            }
        });
        binding.flclose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSheet2();
            }
        });
        binding.flclose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSheet3();
            }
        });
        binding.flclose4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSheet4();
            }
        });
        binding.flDraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOrderModel.setReference_no(binding.editInvoicenum.getText().toString());
                createOrderModel.setSale_status("3");
                createOrderModel.setPaid_by_id("3");
                if (!createOrderModel.getCustomer_id().equals("0")) {
                    createOrder();
                } else {
                    Toast.makeText(CartActivity.this, getResources().getString(R.string.choose_customer), Toast.LENGTH_LONG).show();
                }
            }
        });
        binding.flRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                adapter.notifyDataSetChanged();
                preferences.clearcart_oliva(CartActivity.this);
                createOrderModel = preferences.getcart_olivaData(CartActivity.this);
                updateUi();
            }
        });

        updateUi();
        gettax();
        getCustomer();
    }

    private void checktax() {
        if (binding.spinnerTax.getSelectedItemPosition() == 0) {
            Toast.makeText(this, R.string.choose_tax, Toast.LENGTH_LONG).show();
        } else {
            binding.tvtax.setText(String.format("%.2f", createOrderModel.getOrder_tax()));
            closeSheet();
        }
    }

    private void checkData() {
        if (cashDataModel.isDataValid(this)) {
            createOrderModel.setPaying_amount(Math.round(createOrderModel.getGrand_total()));
            createOrderModel.setPaid_amount(Math.round(Double.parseDouble(cashDataModel.getPaid_amount())));
            createOrderModel.setStaff_note(cashDataModel.getStaff_note());
            createOrderModel.setPayment_note(cashDataModel.getPayment_note());
            createOrderModel.setSale_note(cashDataModel.getSale_note());
            closeSheet4();

        }
    }

    private void checkdiscount() {
        String disount = binding.edtDiscount.getText().toString();
        if (!disount.isEmpty()) {
            double disountvalue = Double.parseDouble(disount);
            binding.tvdiscount.setText(String.format("%.2f", disountvalue));
            createOrderModel.setOrder_discount(disountvalue);
            createOrderModel.setGrand_total(total - ((total * createOrderModel.getOrder_discount()) / 100) + createOrderModel.getOrder_tax());
            cashDataModel.setPaying_amount(Math.round(createOrderModel.getGrand_total()) + "");
            cashDataModel.setPaid_amount(Math.round(createOrderModel.getGrand_total()) + "");
            binding.tvTotal.setText(String.format("%.2f", createOrderModel.getGrand_total()));
            closeSheet2();
        } else {
            binding.edtDiscount.setError(getResources().getString(R.string.field_req));
        }
    }


    @Override
    public void onBackPressed() {
        finish();
    }


    private void updateUi() {
        list.clear();
        if (createOrderModel != null) {
            list.addAll(createOrderModel.getDetails());
            adapter.notifyDataSetChanged();
            binding.llEmptyCart.setVisibility(View.GONE);
            binding.fltotal.setVisibility(View.VISIBLE);
            createOrderModel.setUser_id(userModel.getUser().getId());
            createOrderModel.setWarehouse_id_hidden(userModel.getUser().getWarehouse_id() + "");
            createOrderModel.setWarehouse_id(userModel.getUser().getWarehouse_id() + "");
            createOrderModel.setCoupon_active("");
            createOrderModel.setCoupon_discount("0");
            createOrderModel.setCoupon_id("");
            //createOrderModel.setPaid_by_id("1");
            createOrderModel.setShipping_cost("");
            createOrderModel.setPaid_amount(0);
            createOrderModel.setPayment_note("");
            createOrderModel.setPaying_amount(0);
            createOrderModel.setOrder_tax(0);
            createOrderModel.setSale_note("");
            createOrderModel.setStaff_note("");
            createOrderModel.setCheque_no("");
            createOrderModel.setReference_no("");
            createOrderModel.setGift_card_id("");
            createOrderModel.setPos("1");
            createOrderModel.setSale_status("3");
            createOrderModel.setOrder_tax_rate(0);
            createOrderModel.setOrder_discount(0);
            createOrderModel.setDraft("0");
            createOrderModel.setBiller_id_hidden(userModel.getUser().getBiller_id() + "");
            createOrderModel.setBiller_id(userModel.getUser().getBiller_id() + "");
            createOrderModel.setDelivery_companies_id("");
            createOrderModel.setCustomer_id("0");
            createOrderModel.setCustomer_id_hidden("0");

            calculateTotal();
        }
        else {
            binding.llEmptyCart.setVisibility(View.VISIBLE);
            binding.fltotal.setVisibility(View.GONE);

        }
    }

    private void calculateTotal() {
        total = 0;
        tax = 0;
        qty = 0;
        for (ItemCartModel model : list) {

            total += (model.getNet_unit_price() + ((model.getNet_unit_price() * model.getTax_rate()) / 100)) * model.getQty();
            tax += model.getTax();
            qty += model.getQty();
        }
        createOrderModel.setTotal_price(total);
        createOrderModel.setTotal_tax(tax);
        createOrderModel.setTotal_discount("0");
        createOrderModel.setTotal_qty(qty + "");
        createOrderModel.setItem(list.size() + "");
        createOrderModel.setGrand_total(total - ((total * createOrderModel.getOrder_discount()) / 100) + createOrderModel.getOrder_tax());
        cashDataModel.setPaying_amount(Math.round(createOrderModel.getGrand_total()) + "");
        cashDataModel.setPaid_amount(Math.round(createOrderModel.getGrand_total()) + "");
        binding.setModel(cashDataModel);
        binding.tvTotal.setText(String.format("%.2f", createOrderModel.getGrand_total()));

    }


    public void openSheet() {

        //binding.btnAddBid.setAlpha(.4f);
        binding.expandLayout.setExpanded(true, true);
    }

    public void closeSheet() {
        // binding.btnAddBid.setAlpha(0);
        binding.expandLayout.collapse(true);

    }

    public void openSheet2() {

        //binding.btnAddBid.setAlpha(.4f);
        binding.expandLayout2.setExpanded(true, true);
    }

    public void closeSheet2() {
        // binding.btnAddBid.setAlpha(0);
        binding.expandLayout2.collapse(true);

    }

    public void openSheet3() {

        //binding.btnAddBid.setAlpha(.4f);
        binding.expandLayout3.setExpanded(true, true);
    }

    public void closeSheet3() {
        // binding.btnAddBid.setAlpha(0);
        binding.expandLayout3.collapse(true);

    }

    public void openSheet4() {

        //binding.btnAddBid.setAlpha(.4f);
        binding.expandLayout4.setExpanded(true, true);
    }

    public void closeSheet4() {
        // binding.btnAddBid.setAlpha(0);
        binding.expandLayout4.collapse(true);
        createOrderModel.setSale_status("1");
        if (!createOrderModel.getCustomer_id().equals("0")) {
            createOrder();
        } else {
            Toast.makeText(CartActivity.this, getResources().getString(R.string.choose_customer), Toast.LENGTH_LONG).show();
        }


    }

    public void increase_decrease(ItemCartModel model, int adapterPosition) {
        list.set(adapterPosition, model);
        adapter.notifyItemChanged(adapterPosition);
        createOrderModel.setDetails(list);
        preferences.create_update_cart_oliva(this, createOrderModel);
        calculateTotal();
    }

    public void deleteItem(ItemCartModel model2, int adapterPosition) {
        list.remove(adapterPosition);
        adapter.notifyItemRemoved(adapterPosition);
        createOrderModel.setDetails(list);
        preferences.create_update_cart_oliva(this, createOrderModel);
        calculateTotal();
        if (list.size() == 0) {
            binding.llEmptyCart.setVisibility(View.VISIBLE);
            binding.fltotal.setVisibility(View.GONE);
            preferences.clearcart_oliva(this);
        }
    }

    public void createOrder() {
//        try {

        dialog.show();
        accessDatabase.insertOrder(createOrderModel, CartActivity.this);

//            Api.getService(Tags.base_url)
//                    .createOrder(createOrderModel)
//                    .enqueue(new Callback<SingleOrderDataModel>() {
//                        @Override
//                        public void onResponse(Call<SingleOrderDataModel> call, Response<SingleOrderDataModel> response) {
//                            dialog.dismiss();
//                            if (response.isSuccessful()) {
//
//
//                                if (response.body() != null && response.body().getData() != null) {
//                                    preferences.clearcart_oliva(CartActivity.this);
//                                    createOrderModel = preferences.getcart_olivaData(CartActivity.this);
//                                    updateUi(response.body());
//
//                                    // navigateToOrderDetialsActivity(response.body());
//
//                                }
//
//                            } else {
//                                dialog.dismiss();
//                                try {
//                                    Log.e("error_code", response.errorBody().string());
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//
//                                if (response.code() == 500) {
//                                    // Toast.makeText(CheckoutActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
//
//
//                                } else {
//                                    //Toast.makeText(CheckoutActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
//
//                                    try {
//
//                                        Log.e("error", response.code() + "_" + response.errorBody().string());
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<SingleOrderDataModel> call, Throwable t) {
//                            try {
//                                dialog.dismiss();
//                                if (t.getMessage() != "") {
//                                    Log.e("error", t.getMessage());
//                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
//                                        //      Toast.makeText(CheckoutActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        //    Toast.makeText(CheckoutActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//
//                            } catch (Exception e) {
//                            }
//                        }
//                    });
//        } catch (Exception e) {
//
//        }
    }

    private void updateUi(SingleOrderDataModel body) {
        if (createOrderModel != null) {
            list.addAll(createOrderModel.getDetails());
            adapter.notifyDataSetChanged();
            binding.llEmptyCart.setVisibility(View.GONE);
            binding.fltotal.setVisibility(View.VISIBLE);
            createOrderModel.setUser_id(userModel.getUser().getId());
            createOrderModel.setWarehouse_id_hidden(userModel.getUser().getWarehouse_id() + "");
            createOrderModel.setWarehouse_id(userModel.getUser().getWarehouse_id() + "");
            createOrderModel.setCoupon_active("");
            createOrderModel.setCoupon_discount("0");
            createOrderModel.setCoupon_id("");
            // createOrderModel.setPaid_by_id("1");
            createOrderModel.setShipping_cost("");
            createOrderModel.setPaid_amount(0);
            createOrderModel.setPayment_note("");
            createOrderModel.setPaying_amount(0);
            createOrderModel.setOrder_tax(0);
            createOrderModel.setSale_note("");
            createOrderModel.setStaff_note("");
            createOrderModel.setCheque_no("");
            createOrderModel.setReference_no("");
            createOrderModel.setGift_card_id("");
            createOrderModel.setPos("1");
            createOrderModel.setSale_status("3");
            createOrderModel.setOrder_tax_rate(0);
            createOrderModel.setOrder_discount(0);
            createOrderModel.setDraft("0");
            createOrderModel.setBiller_id_hidden(userModel.getUser().getBiller_id() + "");
            createOrderModel.setBiller_id(userModel.getUser().getBiller_id() + "");
            createOrderModel.setDelivery_companies_id("");
            createOrderModel.setCustomer_id("0");
            createOrderModel.setCustomer_id_hidden("0");

            calculateTotal();
        } else {
            list.clear();
            adapter.notifyDataSetChanged();
            binding.llEmptyCart.setVisibility(View.VISIBLE);
            binding.fltotal.setVisibility(View.GONE);

        }
//        Intent intent = new Intent(CartActivity.this, InvoiceActivity.class);
//        intent.putExtra("data", body.getData().getId() + "");
//        startActivity(intent);
    }

    private void gettax() {
        accessDatabase.getTax(CartActivity.this);

//        taxModelList.clear();
//        spinnerTaxAdapter.notifyDataSetChanged();
//        Api.getService(Tags.base_url)
//                .getTax()
//                .enqueue(new Callback<TaxDataModel>() {
//                    @Override
//                    public void onResponse(Call<TaxDataModel> call, Response<TaxDataModel> response) {
//                        if (response.isSuccessful()) {
//                            if (response.body() != null && response.body().getStatus() == 200) {
//                                if (response.body().getData() != null) {
//                                    if (response.body().getData().size() > 0) {
//                                        taxModelList.add(new TaxModel(getResources().getString(R.string.choose_tax)));
//                                        taxModelList.addAll(response.body().getData());
//                                        spinnerTaxAdapter.notifyDataSetChanged();
//                                    } else {
//
//                                    }
//                                }
//                            } else {
//                                Log.e("kdkdk", response.code() + "");
//                                //  Toast.makeText(SignUpActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
//
//                            }
//
//
//                        } else {
//
//
//                            switch (response.code()) {
//                                case 500:
//                                    //   Toast.makeText(SignUpActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
//                                    break;
//                                default:
//                                    //   Toast.makeText(SignUpActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
//                                    break;
//                            }
//                            try {
//                                Log.e("error_code", response.code() + "_");
//                            } catch (NullPointerException e) {
//
//                            }
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<TaxDataModel> call, Throwable t) {
//                        try {
//
////                            binding.arrow.setVisibility(View.VISIBLE);
////
////                            binding.progBar.setVisibility(View.GONE);
//                            if (t.getMessage() != null) {
//                                Log.e("error", t.getMessage());
//                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
//                                    //     Toast.makeText(SignUpActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
//                                } else if (t.getMessage().toLowerCase().contains("socket") || t.getMessage().toLowerCase().contains("canceled")) {
//                                } else {
//                                    //  Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                        } catch (Exception e) {
//
//                        }
//                    }
//                });

    }

    private void getCustomer() {
        accessDatabase.getCustomer(CartActivity.this);

//        customerModelList.clear();
//        spinnerCustomerAdapter.notifyDataSetChanged();
//        ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
//        dialog.setCancelable(false);
//        dialog.show();
//        Api.getService(Tags.base_url)
//                .getCustomer()
//                .enqueue(new Callback<CustomerDataModel>() {
//                    @Override
//                    public void onResponse(Call<CustomerDataModel> call, Response<CustomerDataModel> response) {
//                        dialog.dismiss();
//                        if (response.isSuccessful()) {
//                            if (response.body() != null && response.body().getStatus() == 200) {
//                                if (response.body().getData() != null) {
//                                    if (response.body().getData().size() > 0) {
//                                        customerModelList.addAll(response.body().getData());
//                                        spinnerCustomerAdapter.notifyDataSetChanged();
//                                    } else {
//
//                                    }
//                                }
//                            } else {
//                                Log.e("kdkdk", response.code() + "");
//                                //  Toast.makeText(SignUpActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
//
//                            }
//
//
//                        } else {
//
//
//                            switch (response.code()) {
//                                case 500:
//                                    //   Toast.makeText(SignUpActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
//                                    break;
//                                default:
//                                    //   Toast.makeText(SignUpActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
//                                    break;
//                            }
//                            try {
//                                Log.e("error_code", response.code() + "_");
//                            } catch (NullPointerException e) {
//
//                            }
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<CustomerDataModel> call, Throwable t) {
//                        try {
//                            dialog.dismiss();
////                            binding.arrow.setVisibility(View.VISIBLE);
////
////                            binding.progBar.setVisibility(View.GONE);
//                            if (t.getMessage() != null) {
//                                Log.e("error", t.getMessage());
//                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
//                                    //     Toast.makeText(SignUpActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
//                                } else if (t.getMessage().toLowerCase().contains("socket") || t.getMessage().toLowerCase().contains("canceled")) {
//                                } else {
//                                    //  Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                        } catch (Exception e) {
//
//                        }
//                    }
//                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (createOrderModel != null) {
            getCustomer();
        }
    }

    @Override
    public void onMainTaxDataSuccess(List<TaxModel> taxModelList) {
        this.taxModelList.clear();
        this.taxModelList.addAll(taxModelList);
        spinnerTaxAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCustomerDataSuccess(List<CustomerModel> customerModelList) {
        this.customerModelList.clear();
        this.customerModelList.addAll(customerModelList);
        spinnerCustomerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onOrderDataInsertedSuccess(long bol) {
      //  Log.e("fffff",bol+"");
        for(int i=0;i<list.size();i++){
            ItemCartModel itemCartModel=list.get(i);
            itemCartModel.setCreate_id((int) bol);
            list.set(i,itemCartModel);
        }
        createOrderModel.setDetails(list);
        if (bol > 0) {
            accessDatabase.insertOrderProduct(createOrderModel.getDetails(), CartActivity.this);

        }
    }

    @Override
    public void onProductORderDataInsertedSuccess(Boolean bol) {
        if (bol) {
            dialog.dismiss();
            preferences.clearcart_oliva(CartActivity.this);
            createOrderModel = preferences.getcart_olivaData(CartActivity.this);
            updateUi();
        }
    }
}