package com.oliva2.activities_fragments.activity_add_customer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.oliva2.R;
import com.oliva2.activities_fragments.activity_cart.CartActivity;
import com.oliva2.adapters.SpinnerCustomerGroupAdapter;
import com.oliva2.databinding.ActivityAddCustomerBinding;
import com.oliva2.language.Language;
import com.oliva2.local_database.AccessDatabase;
import com.oliva2.local_database.DataBaseInterfaces;
import com.oliva2.models.AddCustomerDataModel;
import com.oliva2.models.CustomerGroupDataModel;
import com.oliva2.models.CustomerGroupModel;
import com.oliva2.models.CustomerModel;
import com.oliva2.models.SingleCustomerDataModel;
import com.oliva2.models.UserModel;
import com.oliva2.preferences.Preferences;
import com.oliva2.remote.Api;
import com.oliva2.share.Common;
import com.oliva2.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCustomerActivity extends AppCompatActivity implements DataBaseInterfaces.CustomerGroupInterface, DataBaseInterfaces.CustmomerInsertInterface {
    private ActivityAddCustomerBinding binding;
    private Preferences preferences;
    private String lang;
    private AddCustomerDataModel addCustomerGroupDataModel;
    private List<CustomerGroupModel> customerGroupModelList;
    private SpinnerCustomerGroupAdapter spinnerCustomerGroupAdapter;
    private UserModel userModel;
    private AccessDatabase accessDatabase;
    private int id;

    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_customer);
        getDataFromIntent();
        initView();

    }
    private void getDataFromIntent() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id",1);
    }


    private void initView() {
        accessDatabase = new AccessDatabase(this);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        customerGroupModelList = new ArrayList<>();

        addCustomerGroupDataModel = new AddCustomerDataModel();
        binding.setModel(addCustomerGroupDataModel);
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        spinnerCustomerGroupAdapter = new SpinnerCustomerGroupAdapter(customerGroupModelList, this);
        binding.spinnerGroup.setAdapter(spinnerCustomerGroupAdapter);
        binding.spinnerGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int spinnerpos, long id) {
                //  Log.e("dkdkk",position+"");

                if (spinnerpos > 0) {

                    addCustomerGroupDataModel.setCustomer_group_id(customerGroupModelList.get(spinnerpos).getId() + "");


                } else {

                    addCustomerGroupDataModel.setCustomer_group_id("");


                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getCustomerGroup();
        binding.btnConfirm3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addCustomerGroupDataModel.isDataValid(AddCustomerActivity.this)) {
                    addcustomer();
                }
            }
        });

    }

    private void getCustomerGroup() {
        accessDatabase.getCustomerGroup(AddCustomerActivity.this);

//        ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
//        dialog.setCancelable(false);
//        dialog.show();
//        customerGroupModelList.clear();
//        Api.getService(Tags.base_url)
//                .getCustomerGroup()
//                .enqueue(new Callback<CustomerGroupDataModel>() {
//                    @Override
//                    public void onResponse(Call<CustomerGroupDataModel> call, Response<CustomerGroupDataModel> response) {
//                        dialog.dismiss();
//                        if (response.isSuccessful()) {
//                            if (response.body() != null && response.body().getStatus() == 200) {
//                                if (response.body().getData() != null) {
//                                    if (response.body().getData().size() > 0) {
//                                        customerGroupModelList.add(new CustomerGroupModel(getResources().getString(R.string.choose_customer_group)));
//                                        customerGroupModelList.addAll(response.body().getData());
//                                        spinnerCustomerGroupAdapter.notifyDataSetChanged();
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
//                    public void onFailure(Call<CustomerGroupDataModel> call, Throwable t) {
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

    private void addcustomer() {
        CustomerModel customerModel=new CustomerModel();
        customerModel.setCustomer_group_id(Integer.parseInt(addCustomerGroupDataModel.getCustomer_group_id()));
        customerModel.setUser_id(userModel.getUser().getId());
        customerModel.setName(addCustomerGroupDataModel.getName());
        customerModel.setEmail(addCustomerGroupDataModel.getEmail());
        customerModel.setPhone_number(addCustomerGroupDataModel.getPhone());
        customerModel.setAddress(addCustomerGroupDataModel.getAddress());
        customerModel.setCity(addCustomerGroupDataModel.getCity());
        customerModel.setId(id);
        customerModel.setType("local");
        accessDatabase.insertCustomer(customerModel,this);
        //Log.e("dlldldldl",customerModel.getId()+"");
//        ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
//        dialog.setCancelable(false);
//        dialog.show();
//        Api.getService(Tags.base_url)
//                .addCustomer(addCustomerGroupDataModel.getCustomer_group_id(), userModel.getUser().getId() + "", addCustomerGroupDataModel.getName(), addCustomerGroupDataModel.getEmail(), addCustomerGroupDataModel.getPhone(), addCustomerGroupDataModel.getAddress(), addCustomerGroupDataModel.getCity())
//                .enqueue(new Callback<SingleCustomerDataModel>() {
//                    @Override
//                    public void onResponse(Call<SingleCustomerDataModel> call, Response<SingleCustomerDataModel> response) {
//                        dialog.dismiss();
//                        Log.e("rriir", response.body().getStatus() + "");
//                        if (response.isSuccessful()) {
//                            if (response.body().getStatus() == 200) {
//
//                                finish();
//
//                            }
//
//                        } else {
//                            try {
//                                Log.e("mmmmmmmmmm", response.errorBody().string());
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//
//
//                            if (response.code() == 500) {
//                                Toast.makeText(AddCustomerActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Log.e("mmmmmmmmmm", response.code() + "");
//
//                                // Toast.makeText(LoginActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<SingleCustomerDataModel> call, Throwable t) {
//                        try {
//                            dialog.dismiss();
//                            if (t.getMessage() != null) {
//                                Log.e("msg_category_error", t.toString() + "__");
//
//                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
//                                    //  Toast.makeText(LoginActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
//                                } else {
//                                    //    Toast.makeText(LoginActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        } catch (Exception e) {
//                            Log.e("Error", e.getMessage() + "__");
//                        }
//                    }
//                });

    }

    @Override
    public void onCustomerGroupDataSuccess(List<CustomerGroupModel> customerGroupModelList) {
        this.customerGroupModelList.add(new CustomerGroupModel(getResources().getString(R.string.choose_customer_group)));
        this.customerGroupModelList.addAll(customerGroupModelList);
        spinnerCustomerGroupAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCustmomerInsertedSuccess(long bol) {
        if(bol>0){
            finish();
        }
    }
}
