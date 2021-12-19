package com.oliva2.activities_fragments.activity_home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.oliva2.R;
import com.oliva2.activities_fragments.activity_cart.CartActivity;
import com.oliva2.activities_fragments.activity_cash_register_detials.CashRegisterDetialsActivity;
import com.oliva2.activities_fragments.activity_discount_day.DiscountDayActivity;
import com.oliva2.activities_fragments.activity_invoice.InvoiceActivity;
import com.oliva2.adapters.HomeAdapter;
import com.oliva2.adapters.MarkAdapter;
import com.oliva2.adapters.ProductAdapter;
import com.oliva2.adapters.ProductDetialsAdapter;
import com.oliva2.databinding.ActivityHomeBinding;
import com.oliva2.language.Language;
import com.oliva2.local_database.AccessDatabase;
import com.oliva2.local_database.DataBaseInterfaces;
import com.oliva2.models.BrandDataModel;
import com.oliva2.models.BrandModel;
import com.oliva2.models.CategoryDataModel;
import com.oliva2.models.CategoryModel;
import com.oliva2.models.CreateOrderModel;
import com.oliva2.models.InvoiceModel;
import com.oliva2.models.ItemCartModel;
import com.oliva2.models.ProductDataModel;
import com.oliva2.models.ProductDetialsModel;
import com.oliva2.models.ProductModel;
import com.oliva2.models.StatusResponse;
import com.oliva2.models.UserModel;
import com.oliva2.preferences.Preferences;
import com.oliva2.remote.Api;
import com.oliva2.share.Common;
import com.oliva2.tags.Tags;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements DataBaseInterfaces.RetrieveInsertInterface, DataBaseInterfaces.CategoryInsertInterface, DataBaseInterfaces.CategoryInterface, DataBaseInterfaces.ProductInterface, DataBaseInterfaces.FirstStockInsertInterface, DataBaseInterfaces.TaxInsertInterface, DataBaseInterfaces.UnitInsertInterface, DataBaseInterfaces.OfferInsertInterface, DataBaseInterfaces.BrandInsertInterface, DataBaseInterfaces.BrandInterface {
    private ActivityHomeBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private String lang;
    private ActionBarDrawerToggle toggle;
    private ActivityResultLauncher<Intent> launcher;
    private ActivityResultLauncher<Intent> launcher2;
    private AccessDatabase accessDatabase;

    private String type;
    private List<CategoryModel> categoryModelList;
    private HomeAdapter homeAdapter;
    private List<BrandModel> brandModelList;
    private List<ProductModel> productModelList;
    private MarkAdapter markAdapter;
    private ProductAdapter productAdapter;
    private ProductDetialsAdapter productDetialsAdapter;
    private List<ProductModel> productDetialsModelList;
    private List<ProductDetialsModel> allproductDetialsModelList;
    private int pos = -1;
    private ProductModel productModel;
    private List<Integer> productids;
    private List<Integer> productindex;
    private List<Integer> categoryindex;

    private int times;
    public int category_id;
    private int check = -1;
    private byte[] byteArray;
    private Bitmap bitmapimage;
    private Runnable r;
    private int i;
    private int j;

    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {

            type = intent.getStringExtra("type");

        }
    }

    private void initView() {
        accessDatabase = new AccessDatabase(this);
        categoryModelList = new ArrayList<>();
        categoryindex = new ArrayList<>();
        categoryindex.add(2);
        categoryindex.add(3);
        categoryindex.add(4);
        categoryindex.add(5);
        categoryindex.add(13);
        categoryindex.add(16);
        brandModelList = new ArrayList<>();
        productModelList = new ArrayList<>();
        productDetialsModelList = new ArrayList<>();
        allproductDetialsModelList = new ArrayList<>();
        productids = new ArrayList<>();
        productindex = new ArrayList<>();
        preferences = Preferences.getInstance();
        getCartItemCount();
        userModel = preferences.getUserData(this);
        homeAdapter = new HomeAdapter(categoryModelList, this);
        markAdapter = new MarkAdapter(brandModelList, this);
        productAdapter = new ProductAdapter(productModelList, this);
        productDetialsAdapter = new ProductDetialsAdapter(allproductDetialsModelList, this);
        binding.recviewCategory.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.recviewCategory.setAdapter(homeAdapter);
        binding.recView.setLayoutManager(new GridLayoutManager(this, 3));
        binding.recView.setAdapter(productAdapter);
        binding.recViewmark.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.recViewmark.setAdapter(markAdapter);
        binding.recviewDetials.setLayoutManager(new LinearLayoutManager(this));
        binding.recviewDetials.setAdapter(productDetialsAdapter);
//        if (userModel != null) {
//            binding.setModel(userModel);
//        }
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);

        toggle = new ActionBarDrawerToggle(this, binding.drawar, binding.toolbar, R.string.open, R.string.close);
//
//        toggle.setHomeAsUpIndicator(R.drawable.ic_menu);


        toggle.syncState();


        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                lang = result.getData().getStringExtra("lang");
                //   refreshActivity(lang);
            }

        });
        launcher2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            checkAvialbilty();

        });
        binding.llCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        binding.flCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        binding.flclose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeSheet3();
            }
        });
        binding.llInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawar.closeDrawer(GravityCompat.START);

                Intent intent = new Intent(HomeActivity.this, InvoiceActivity.class);
                intent.putExtra("data", "0");
                startActivity(intent);
//                getlastInvoice();
//                Intent intent = new Intent(HomeActivity.this, InvoiceActivity.class);
//                startActivity(intent);
            }
        });
        binding.llcashRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawar.closeDrawer(GravityCompat.START);

                check = 0;
                checkAvialbilty();
            }
        });
        binding.llCoalition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawar.closeDrawer(GravityCompat.START);

                Intent intent = new Intent(HomeActivity.this, DiscountDayActivity.class);
                startActivity(intent);
            }
        });
        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mony = binding.edtMony.getText().toString();
                if (!mony.trim().isEmpty()) {
                    enterMony(mony);
                } else {
                    binding.edtMony.setError(getResources().getString(R.string.field_req));
                }
            }
        });
        checkAvialbilty();
        if (userModel != null) {
//            EventBus.getDefault().register(this);
//            getNotificationCount();
//            updateTokenFireBase();
//            updateLocation();
        }

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
        binding.btnchoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ItemCartModel> productDetailsList;
                CreateOrderModel add_order_model = preferences.getCartData(HomeActivity.this);
                if (add_order_model != null) {
                    productDetailsList = add_order_model.getDetails();
                } else {
                    add_order_model = new CreateOrderModel();
                    productDetailsList = new ArrayList<>();
                }
                //Log.e("ldkdkdkkd", productids.size() + " " + productModel.getTimes());
                if (productids.size() == times) {
                    binding.checkbox.setChecked(false);
                    allproductDetialsModelList.clear();
                    if (productModel.getFirst_stock().getQty() > 0) {
                        ItemCartModel productDetails = new ItemCartModel();
                        productDetails.setQty(1);
                        productDetails.setImage(productModel.getImage());
                        productDetails.setName(productModel.getName());
                        productDetails.setNet_unit_price(productModel.getPrice());
                        productDetails.setProduct_id(productModel.getId());
                        productDetails.setProduct_batch_id("");
                        productDetails.setProduct_code(productModel.getCode());
                        productDetails.setDiscount(0);
                        productDetails.setStock((int) productModel.getFirst_stock().getQty());

                        if (productModel.getTax() != null) {
                            productDetails.setTax((productModel.getPrice() * productModel.getTax().getRate()) / 100);
                            productDetails.setTax_rate(productModel.getTax().getRate());
                            productDetails.setSubtotal((((productModel.getPrice() * productModel.getTax().getRate()) / 100) + productModel.getPrice()) * productDetails.getQty());

                        } else {
                            productDetails.setSubtotal(productModel.getPrice() * productDetails.getQty());

                        }
                        if (productModel.getUnit() != null) {
                            productDetails.setSale_unit(productModel.getUnit().getUnit_name());
                        } else {
                            productDetails.setSale_unit("n/a");
                        }
                        productDetails.setProducts_id(productids);
                        productDetailsList.add(productDetails);
                        add_order_model.setDetails(productDetailsList);
                        productModel.setCount(1);
                        productModelList.set(pos, productModel);
                        productAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(HomeActivity.this, getResources().getString(R.string.unvailable), Toast.LENGTH_LONG).show();
                    }

                    preferences.create_update_cart(HomeActivity.this, add_order_model);
                    getCartItemCount();
                    closeSheet2();

                } else {
                    Toast.makeText(HomeActivity.this, getResources().getString(R.string.choose_product), Toast.LENGTH_LONG).show();
                }
            }
        });

        //  getProdusts("0", "0", "1");
        category_id = 0;
        // binding.recviewCategory.setNestedScrollingEnabled(true);
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo != null) {
            getCategory();
            getBrands();
        } else {
            accessDatabase.getBrand(this);
            accessDatabase.getCategory(this);
            accessDatabase.getProduct(this,  "1","featured");
        }

    }

    public void openSheet() {

        //binding.btnAddBid.setAlpha(.4f);
        binding.expandLayout.setExpanded(true, true);
        accessDatabase.getBrand(this);

        //   getBrands();
    }

    public void closeSheet() {
        // binding.btnAddBid.setAlpha(0);
        binding.expandLayout.collapse(true);

    }

    public void openSheet2(ProductModel productModel, int pos) {

        //binding.btnAddBid.setAlpha(.4f);
        binding.expandLayout2.setExpanded(true, true);


    }

    public void closeSheet2() {
        // binding.btnAddBid.setAlpha(0);
        productids.clear();
        productindex.clear();
        binding.checkbox.setChecked(false);
        allproductDetialsModelList.clear();
        productDetialsAdapter.notifyDataSetChanged();
        binding.expandLayout2.collapse(true);

    }

    public void openSheet3() {

        //binding.btnAddBid.setAlpha(.4f);
        binding.expandLayout3.setExpanded(true, true);
        //getBrands();
    }

    public void closeSheet3() {
        // binding.btnAddBid.setAlpha(0);
        binding.expandLayout3.collapse(true);
        if (check == 0) {
            check = -1;
            Intent intent = new Intent(HomeActivity.this, CashRegisterDetialsActivity.class);
            launcher2.launch(intent);
        }

    }

    private void getCategory() {
        categoryModelList.clear();
        homeAdapter.notifyDataSetChanged();

        Api.getService(Tags.base_url)
                .getDepartments()
                .enqueue(new Callback<CategoryDataModel>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(Call<CategoryDataModel> call, Response<CategoryDataModel> response) {
                        // binding.progBarCategory.setVisibility(View.GONE);
                        if (response.isSuccessful()) {

                            if (response.body() != null && response.body().getStatus() == 200) {
                                if (response.body().getData() != null) {
                                    if (response.body().getData().size() > 0) {
                                        updatedata(response.body());
                                    } else {
                                        //               binding.tvNoDataCategory.setVisibility(View.VISIBLE);

                                    }
                                }
                            } else {
                                //     binding.tvNoDataCategory.setVisibility(View.VISIBLE);

                                //  Toast.makeText(SignUpActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                            }


                        } else {

                            //binding.tvNoDataCategory.setVisibility(View.VISIBLE);

                            switch (response.code()) {
                                case 500:
                                    //   Toast.makeText(SignUpActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    //   Toast.makeText(SignUpActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                            try {
                                Log.e("error_code", response.code() + "_");
                            } catch (NullPointerException e) {

                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<CategoryDataModel> call, Throwable t) {
                        try {
                            accessDatabase.getCategory(HomeActivity.this);

                            //binding.progBarCategory.setVisibility(View.GONE);
                            //binding.tvNoDataCategory.setVisibility(View.VISIBLE);

//                            binding.arrow.setVisibility(View.VISIBLE);
//
//                            binding.progBar.setVisibility(View.GONE);
                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage());
                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    //     Toast.makeText(SignUpActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else if (t.getMessage().toLowerCase().contains("socket") || t.getMessage().toLowerCase().contains("canceled")) {
                                } else {
                                    //  Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        } catch (Exception e) {

                        }
                    }
                });

    }

    private void updatedata(CategoryDataModel body) {
        List<CategoryModel> categoryModelList = body.getData();
        this.categoryModelList.addAll(categoryModelList);
        // categoryModelList.addAll(this.categoryModelList);

        for (i = 0; i < categoryModelList.size(); i++) {
            CategoryModel categoryModel = categoryModelList.get(i);

                    setImageBitmap(Tags.Category_IMAGE_URL + categoryModel.getImage(), categoryModel, i);


        }

        // Log.e("lll",body.getData().size()+"");

        accessDatabase.insertCategory(this.categoryModelList, HomeActivity.this);
        accessDatabase.getCategory(this);

    }

    private void updateDate(CategoryDataModel body) {
        categoryModelList.addAll(body.getData());
        homeAdapter.notifyDataSetChanged();

        new Handler()
                .postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Log.e("dkdkk","slldlk");
                        binding.recviewCategory.scrollToPosition(0);
                        //  binding.recviewCategory.smoothScrollToPosition(0);
                    }
                }, 10);
    }

    private void getBrands() {
        brandModelList.clear();
        markAdapter.notifyDataSetChanged();
        binding.progBarBrand.setVisibility(View.VISIBLE);
        binding.tvNoDataBrand.setVisibility(View.GONE);
        Api.getService(Tags.base_url)
                .getBrands()
                .enqueue(new Callback<BrandDataModel>() {
                    @Override
                    public void onResponse(Call<BrandDataModel> call, Response<BrandDataModel> response) {
                        binding.progBarBrand.setVisibility(View.GONE);
                        if (response.isSuccessful()) {

                            if (response.body() != null && response.body().getStatus() == 200) {
                                if (response.body().getData() != null) {
                                    if (response.body().getData().size() > 0) {
//                                        brandModelList.addAll(response.body().getData());
//                                        markAdapter.notifyDataSetChanged();
                                        accessDatabase.insertBrand(response.body().getData(), HomeActivity.this);

                                    } else {
                                        binding.tvNoDataBrand.setVisibility(View.VISIBLE);

                                    }
                                }
                            } else {
                                binding.tvNoDataBrand.setVisibility(View.VISIBLE);

                                //  Toast.makeText(SignUpActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                            }


                        } else {

                            binding.tvNoDataBrand.setVisibility(View.VISIBLE);

                            switch (response.code()) {
                                case 500:
                                    //   Toast.makeText(SignUpActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    //   Toast.makeText(SignUpActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                            try {
                                Log.e("error_code", response.code() + "_");
                            } catch (NullPointerException e) {

                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<BrandDataModel> call, Throwable t) {
                        try {
                            binding.progBarBrand.setVisibility(View.GONE);
                            binding.tvNoDataBrand.setVisibility(View.VISIBLE);

//                            binding.arrow.setVisibility(View.VISIBLE);
//
//                            binding.progBar.setVisibility(View.GONE);
                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage());
                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    //     Toast.makeText(SignUpActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else if (t.getMessage().toLowerCase().contains("socket") || t.getMessage().toLowerCase().contains("canceled")) {
                                } else {
                                    //  Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        } catch (Exception e) {

                        }
                    }
                });

    }

    @SuppressLint("NotifyDataSetChanged")
    public void getProdusts(String brand_id, String cat_id, String isfeatured) {
        Log.e("kdkdkkd", userModel.getUser().getId() + "");
        productModelList.clear();
        productAdapter.notifyDataSetChanged();
        binding.progBar.setVisibility(View.VISIBLE);
        binding.tvNoData.setVisibility(View.GONE);
        Api.getService(Tags.base_url)
                .getmainproduct(userModel.getUser().getId() + "")
                .enqueue(new Callback<ProductDataModel>() {
                    @Override
                    public void onResponse(Call<ProductDataModel> call, Response<ProductDataModel> response) {
                        binding.progBar.setVisibility(View.GONE);
                        if (response.isSuccessful()) {

                            if (response.body() != null && response.body().getStatus() == 200) {
                                if (response.body().getData() != null) {
                                    if (response.body().getData().size() > 0) {

                                        updateData(response.body());
                                    } else if (response.body().getStatus() == 405) {

                                        Toast.makeText(HomeActivity.this, getResources().getString(R.string.permisson), Toast.LENGTH_LONG).show();
                                    } else {
                                        binding.tvNoData.setVisibility(View.VISIBLE);

                                    }
                                }
                            } else {
                                binding.tvNoData.setVisibility(View.VISIBLE);
                                Log.e("kdkdk", response.code() + "");
                                //  Toast.makeText(SignUpActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                            }


                        } else {

                            binding.tvNoData.setVisibility(View.VISIBLE);

                            switch (response.code()) {
                                case 500:
                                    //   Toast.makeText(SignUpActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    //   Toast.makeText(SignUpActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                            try {
                                Log.e("error_code", response.code() + "_");
                            } catch (NullPointerException e) {

                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<ProductDataModel> call, Throwable t) {
                        try {
                            binding.progBar.setVisibility(View.GONE);
                            binding.tvNoData.setVisibility(View.VISIBLE);
                            accessDatabase.getProduct(HomeActivity.this, isfeatured,"featured");

//                            binding.arrow.setVisibility(View.VISIBLE);
//
//                            binding.progBar.setVisibility(View.GONE);
                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage());
                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    //     Toast.makeText(SignUpActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else if (t.getMessage().toLowerCase().contains("socket") || t.getMessage().toLowerCase().contains("canceled")) {
                                } else {
                                    //  Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        } catch (Exception e) {

                        }
                    }
                });

    }

    private void updateData(ProductDataModel body) {
        this.productModelList.clear();
        this.productModelList.addAll(body.getData());
        // Log.e("dlldldlssss", productModelList.size() + "");
        for (j = 0; j < productModelList.size(); j++) {
            ProductModel productModel = productModelList.get(j);

                    setImageBitmap(Tags.Product_IMAGE_URL +productModel.getImage(), productModel, j);

            //

        }
        accessDatabase.insertRetrieve(productModelList, HomeActivity.this);
        //   accessDatabase.getProduct(this);


    }


    public void showBrand(String s) {
        closeSheet();
        category_id = 0;
        accessDatabase.getProduct(HomeActivity.this, s,  "brand_id");

    }

    public void showCategory(String s) {
        productModelList.clear();
        productAdapter.notifyDataSetChanged();
        category_id = Integer.parseInt(s);
        accessDatabase.getProduct(HomeActivity.this,  s, "category_id");


    }


    public void getCartItemCount() {
        if (preferences.getCartData(this) != null && preferences.getCartData(this).getDetails() != null) {
            //  view.onCartCountUpdate(preferences.getCartData(context).getCartModelList().size());
            binding.setCartcount(preferences.getCartData(this).getDetails().size());

        } else {
            binding.setCartcount(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (preferences != null) {
            getCartItemCount();
            category_id = 0;

            // getProdusts("0", "0", "1");
        }
    }

    public void getlastInvoice() {

        ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();


        Api.getService(Tags.base_url)
                .getLatestSale(userModel.getUser().getId() + "")
                .enqueue(new Callback<InvoiceModel>() {
                    @Override
                    public void onResponse(Call<InvoiceModel> call, Response<InvoiceModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getStatus() == 200) {
                                if (response.body().getData() != null) {
                                    try {
                                        binding.drawar.closeDrawer(GravityCompat.START);
                                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(response.body().getData()));
                                        startActivity(myIntent);
                                    } catch (ActivityNotFoundException e) {
//                                        Toast.makeText(this, "No application can handle this request."
//                                                + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
                                        e.printStackTrace();
                                    }
//                                    Intent intent = new Intent(HomeActivity.this, InvoiceActivity.class);
//                                    intent.putExtra("data", response.body().getData());
//                                    startActivity(intent);
                                } else if (response.body().getStatus() == 400) {
                                    Toast.makeText(HomeActivity.this, getResources().getString(R.string.no_invoice), Toast.LENGTH_SHORT).show();

                                }

                            }

                        } else {
                            if (response.code() == 500) {
                                Toast.makeText(HomeActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("ERROR", response.message() + "");

                                //     Toast.makeText(HomeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }


                        }
                    }

                    @Override
                    public void onFailure(Call<InvoiceModel> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            if (t.getMessage() != null) {
                                Log.e("msg_category_error", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    // Toast.makeText(SubscriptionActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else {
                                    //Toast.makeText(SubscriptionActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
    }


    public void checkAvialbilty() {
        ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .checkAvilabilty(userModel.getUser().getId() + "", userModel.getUser().getWarehouse_id() + "")
                .enqueue(new Callback<StatusResponse>() {
                    @Override
                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {

                            if (response.body().getStatus() == 200) {
                                if (check == 0) {
                                    Intent intent = new Intent(HomeActivity.this, CashRegisterDetialsActivity.class);
                                    launcher2.launch(intent);
                                }

                            } else if (response.body().getStatus() == 1000) {
                                openSheet3();
                                //  Log.e("kdkdk", response.code() + "");
                                //  Toast.makeText(SignUpActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                            }


                        } else {


                            switch (response.code()) {
                                case 500:
                                    //   Toast.makeText(SignUpActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    //   Toast.makeText(SignUpActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                            try {
                                Log.e("error_code", response.code() + "_");
                            } catch (NullPointerException e) {

                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<StatusResponse> call, Throwable t) {
                        try {
                            dialog.dismiss();
//                            binding.arrow.setVisibility(View.VISIBLE);
//
//                            binding.progBar.setVisibility(View.GONE);
                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage());
                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    //     Toast.makeText(SignUpActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else if (t.getMessage().toLowerCase().contains("socket") || t.getMessage().toLowerCase().contains("canceled")) {
                                } else {
                                    //  Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        } catch (Exception e) {

                        }
                    }
                });

    }

    public void enterMony(String mony) {
        ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .enterMony(userModel.getUser().getId() + "", userModel.getUser().getWarehouse_id() + "", mony)
                .enqueue(new Callback<StatusResponse>() {
                    @Override
                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {

                            if (response.body().getStatus() == 200) {
                                closeSheet3();
                            }


                        } else {


                            switch (response.code()) {
                                case 500:
                                    //   Toast.makeText(SignUpActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    //   Toast.makeText(SignUpActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                            try {
                                Log.e("error_code", response.code() + "_");
                            } catch (NullPointerException e) {

                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<StatusResponse> call, Throwable t) {
                        try {
                            dialog.dismiss();
//                            binding.arrow.setVisibility(View.VISIBLE);
//
//                            binding.progBar.setVisibility(View.GONE);
                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage());
                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    //     Toast.makeText(SignUpActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else if (t.getMessage().toLowerCase().contains("socket") || t.getMessage().toLowerCase().contains("canceled")) {
                                } else {
                                    //  Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        } catch (Exception e) {

                        }
                    }
                });

    }

    public void addtocart(ProductModel productModel, int layoutPosition) {
    }

    @Override
    public void onRetrieveDataSuccess() {
        for (int i = 0; i < productModelList.size(); i++) {
            if (productModelList.get(i).getFirst_stock() != null) {
                accessDatabase.insertFirst(productModelList.get(i).getFirst_stock(), this);
            }
            if (productModelList.get(i).getUnit() != null) {
                productModelList.get(i).getUnit().setProduct_id(productModelList.get(i).getId());
                accessDatabase.insertUnit(productModelList.get(i).getUnit(), this);

            }
            if (productModelList.get(i).getTax() != null) {
                productModelList.get(i).getTax().setProduct_id(productModelList.get(i).getId());

                accessDatabase.insertTax(productModelList.get(i).getTax(), this);

            }
            if (productModelList.get(i).getOffer_products() != null) {
                accessDatabase.insertOffer(productModelList.get(i).getOffer_products(), HomeActivity.this);

            }
        }
        accessDatabase.getProduct(HomeActivity.this,  "1","featured");

    }

    @Override
    public void onCategoryDataInsertedSuccess() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo != null) {
            getProdusts("0", "0", "0");
        }
    }

    @Override
    public void onCategoryDataSuccess(List<CategoryModel> categoryModelList) {
        this.categoryModelList.clear();
        this.categoryModelList.addAll(categoryModelList);

        homeAdapter.notifyDataSetChanged();
    }

    public void setImageBitmap(String basurl, CategoryModel categoryModel, int pos) {
        Picasso.get().load(basurl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                            bitmapimage = bitmap;
                            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                            bitmapimage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                            categoryModel.setImageBitmap(outputStream.toByteArray());


                            if (i < categoryModelList.size()) {
                                categoryModelList.set(pos, categoryModel);
                            }
//                    binding.imageNotification.setImageBitmap(bitmap);
                            outputStream.flush();
                            outputStream.close();
                        } catch(
                        IOException e)

                        {
                            e.printStackTrace();
                        }
                    }}).start();
              //  Toast.makeText(getApplicationContext(), "Image Downloaded", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

//        Glide.with(this)
//                .asBitmap()
//                .load(basurl)
//                .into(new CustomTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                        bitmapimage = resource;
//                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                        bitmapimage.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                        categoryModel.setImageBitmap(stream.toByteArray());
//
//                        categoryModelList.set(i, categoryModel);                  }
//
//                    @Override
//                    public void onLoadCleared(@Nullable Drawable placeholder) {
//
//                    }
//                });
    }

    public void setImageBitmap(String basurl, ProductModel productModel, int pos) {

//        Picasso.get().load(basurl).into(new Target() {
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//
//                try {
//                    bitmapimage = bitmap;
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    bitmapimage.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                    productModel.setImageBitmap(stream.toByteArray());
//                    productModelList.set(i, productModel);
//
//                } catch (Exception e) {
//                }
//
//
//            }
//
//            @Override
//            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//
//            }
//
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//            }
//        });
        Glide.with(this)
                .asBitmap()
                .load(basurl)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                bitmapimage = resource;
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                bitmapimage.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                                productModel.setImageBitmap(stream.toByteArray());
                                if (pos < productModelList.size()) {
                                    productModelList.set(pos, productModel);
                                }

                            }

                        }
                        ).start();
                    }
                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }

    @Override
    public void onProductDataSuccess(List<ProductModel> productModelList) {
        this.productModelList.clear();
        this.productModelList.addAll(productModelList);
        productAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFirstStockDataSuccess() {

    }

    @Override
    public void onTaxDataSuccess() {

    }

    @Override
    public void onUnitDataSuccess() {

    }

    @Override
    public void onOfferDataInsertedSuccess() {

    }

    @Override
    public void onBrandDataInsertedSuccess() {

    }

    public void getOfflineProdusts(String brand_id, String cat_id, String isfeatured) {
        accessDatabase.getProduct(HomeActivity.this, isfeatured,"featured");

    }

    @Override
    public void onBrandDataSuccess(List<BrandModel> brandModelList) {
        this.brandModelList.clear();
        this.brandModelList.addAll(brandModelList);
        markAdapter.notifyDataSetChanged();
    }
}
