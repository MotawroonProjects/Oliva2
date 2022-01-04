package com.oliva2.activities_fragments.activity_home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
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
import com.oliva2.models.CustomerDataModel;
import com.oliva2.models.CustomerGroupDataModel;
import com.oliva2.models.CustomerGroupModel;
import com.oliva2.models.InvoiceModel;
import com.oliva2.models.ItemCartModel;
import com.oliva2.models.ProductDataModel;
import com.oliva2.models.ProductDetialsModel;
import com.oliva2.models.ProductModel;
import com.oliva2.models.StatusResponse;
import com.oliva2.models.TaxDataModel;
import com.oliva2.models.TaxModel;
import com.oliva2.models.UserModel;
import com.oliva2.preferences.Preferences;
import com.oliva2.remote.Api;
import com.oliva2.share.Common;
import com.oliva2.tags.Tags;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements DataBaseInterfaces.RetrieveInsertInterface, DataBaseInterfaces.CategoryInsertInterface, DataBaseInterfaces.CategoryInterface, DataBaseInterfaces.ProductInterface, DataBaseInterfaces.FirstStockInsertInterface, DataBaseInterfaces.TaxInsertInterface, DataBaseInterfaces.UnitInsertInterface, DataBaseInterfaces.OfferInsertInterface, DataBaseInterfaces.BrandInsertInterface, DataBaseInterfaces.BrandInterface, DataBaseInterfaces.TaxInterface, DataBaseInterfaces.FirstStockInterface, DataBaseInterfaces.UnitInterface, DataBaseInterfaces.ProductOffersInterface, DataBaseInterfaces.FristStockupdateInterface, DataBaseInterfaces.ProductupdateInterface, DataBaseInterfaces.AllProductInterface, DataBaseInterfaces.CustomerGroupInsertInterface, DataBaseInterfaces.MainTaxInsertInterface, DataBaseInterfaces.CustomerInsertInterface {
    private ActivityHomeBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private String lang;
    private ActionBarDrawerToggle toggle;
    private ActivityResultLauncher<Intent> launcher;
    private ActivityResultLauncher<Intent> launcher2;
    private AccessDatabase accessDatabase;
    int page = 1;
    private List<ProductModel> productModels, allproduct;
    private List<Integer> productindex;
    private List<CategoryModel> categoryModelList;
    private HomeAdapter homeAdapter;
    private List<BrandModel> brandModelList;
    private List<ProductModel> productModelList, productModelList1;
    private MarkAdapter markAdapter;
    private ProductAdapter productAdapter;
    private ProductDetialsAdapter productDetialsAdapter;
    // private List<ProductDetialsModel> allproductDetialsModelList;
    private List<Integer> categoryindex;
    public int category_id;
    private String searchtype, id;
    private int check = -1;
    private Bitmap bitmapimage;
    private int productinsert, categoryinsert = 0, brandinsert, taxinsert, unitinsert, firststockinsert, offerinsert;
    private ProgressDialog progressDialog;
    private int layoutpos;
    private boolean getall = false;
    private int layoutpos2 = 0;

    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        initView();

    }


    private void initView() {
        progressDialog = Common.createProgressDialog(this, getString(R.string.wait));
        progressDialog.setCancelable(false);
        productModels = new ArrayList<>();
        allproduct = new ArrayList<>();
        accessDatabase = new AccessDatabase(this);
        categoryModelList = new ArrayList<>();
        categoryindex = new ArrayList<>();
        categoryindex.add(2);
        categoryindex.add(3);
        categoryindex.add(4);
        categoryindex.add(5);
        categoryindex.add(8);
        categoryindex.add(13);
        categoryindex.add(16);
        brandModelList = new ArrayList<>();
        productModelList = new ArrayList<>();
        productModelList1 = new ArrayList<>();
        //  allproductDetialsModelList = new ArrayList<>();
        //productids = new ArrayList<>();
        productindex = new ArrayList<>();
        preferences = Preferences.getInstance();
        getCartItemCount();
        userModel = preferences.getUserData(this);
        homeAdapter = new HomeAdapter(categoryModelList, this);
        markAdapter = new MarkAdapter(brandModelList, this);
        productAdapter = new ProductAdapter(productModelList, this);
        // productDetialsAdapter = new ProductDetialsAdapter(allproductDetialsModelList, this);
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

        category_id = 0;
        binding.imageupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        accessDatabase.clear();

                    }
                }).start();
                productinsert = 0;
                categoryinsert = 0;
                brandinsert = 0;
                taxinsert = 0;
                unitinsert = 0;
                firststockinsert = 0;
                offerinsert = 0;
                getCategory();
                getBrands();
                getProdusts("0", "0", "0");
                getCustomerGroup();
                gettax();
            }
        });
        // binding.recviewCategory.setNestedScrollingEnabled(true);
//        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
//        if (netInfo != null) {
//            getCategory();
//            getBrands();
//            getProdusts("0", "0", "0");
//
//        } else {
        productModelList.clear();
//
        searchtype = "featured";
        id = "1";
        accessDatabase.getBrand(this);
        accessDatabase.getCategory(this);
        // accessDatabase.getProduct(this, "1", "featured", 10, 0);
        accessDatabase.getallProduct(HomeActivity.this);
//        }
        binding.recView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e("llll", dy + " " + dx);
                if (dy > 0) {
                    Log.e(";lllll", "lkllkkkk");
                    int threshold = 20;
                    int count = productAdapter.getItemCount();
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int lastVisible = layoutManager.findLastVisibleItemPosition();
                    if ((lastVisible >= count - threshold)) {
                        //   accessDatabase.getProduct(HomeActivity.this,id,searchtype,10,productModelList.size());
                    }
                }
            }
        });
        binding.nested.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (v.getChildAt(v.getChildCount() - 1) != null) {
                if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                        scrollY > oldScrollY) {
                    accessDatabase.getProduct(HomeActivity.this, id, searchtype, 10, productModelList.size());
                    page += 1;
                    //code to fetch more data for endless scrolling
                }
            }
        });

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
        //productids.clear();
        productindex.clear();
        binding.checkbox.setChecked(false);
        //  allproductDetialsModelList.clear();
        // productDetialsAdapter.notifyDataSetChanged();
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
                                case 1000:
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
                            //    accessDatabase.getCategory(HomeActivity.this);

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
        insertcategory();


        // accessDatabase.getCategory(HomeActivity.this);

        // Log.e("lll",body.getData().size()+"");


    }

    private void insertcategory() {
        CategoryModel categoryModel = categoryModelList.get(categoryinsert);
        if (categoryModel.getImage() != null) {
            Log.e("lldldl", ";ss;;s;s;s" + categoryinsert + " " + categoryModelList.size());

            setImageBitmap(Tags.Category_IMAGE_URL + categoryModel.getImage(), categoryModel, categoryinsert);
        } else {
            // Log.e("lldldl", ";ss;;s;s;s");
            accessDatabase.insertCategory(categoryModel, this);


        }
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
                                        updateData(response.body());
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
                                case 1000:
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

    private void updateData(BrandDataModel body) {
        Log.e("fkkfkfk", body.getData().size() + "");
        brandModelList.clear();
        brandModelList.addAll(body.getData());
        insertbrand();


    }

    private void insertbrand() {
        if (brandModelList.get(brandinsert).getImage() != null) {
            setImageBitmap(Tags.Brand_IMAGE_URL + brandModelList.get(brandinsert).getImage(), brandModelList.get(brandinsert), brandinsert);
        } else {
            accessDatabase.insertBrand(brandModelList.get(brandinsert), this);

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void getProdusts(String brand_id, String cat_id, String isfeatured) {
        progressDialog.show();
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
                                    } else if (response.body().getStatus() == 401) {

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
                                case 1000:
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
                            //  accessDatabase.getProduct(HomeActivity.this, isfeatured, "featured");

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
        this.productModelList1.clear();

        this.productModelList1.addAll(body.getData());
        // Log.e("dlldldlssss", productModelList.size() + "");

        insertproduct();


        //


        //  accessDatabase.getProduct(HomeActivity.this, "1", "featured", 10, 1);

        //   accessDatabase.getProduct(this);


    }

    private void insertproduct() {
        Log.e("kskkskks", productinsert + "");
        ProductModel productModel = productModelList1.get(productinsert);
        if (productModel.getImage() != null) {
            setImageBitmap(Tags.Product_IMAGE_URL + productModel.getImage(), productModel, productinsert);
        } else {

            accessDatabase.insertRetrieve(productModel, HomeActivity.this);


        }
    }


    public void showBrand(String s) {
        closeSheet();
        category_id = 0;
        searchtype = "brand_id";
        id = s;
        productModelList.clear();

        accessDatabase.getProduct(HomeActivity.this, s, "brand_id", 10, 1);

    }

    public void showCategory(String s) {
        productModelList.clear();
        productAdapter.notifyDataSetChanged();
        category_id = Integer.parseInt(s);
        searchtype = "category_id";
        id = s;

        accessDatabase.getProduct(HomeActivity.this, s, "category_id", 10, 1);


    }


    public void getCartItemCount() {
        if (preferences.getcart_olivaData(this) != null && preferences.getcart_olivaData(this).getDetails() != null) {
            //  view.onCartCountUpdate(preferences.getCartData(context).getCartModelList().size());
            binding.setCartcount(preferences.getcart_olivaData(this).getDetails().size());

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
                            if (response.code() == 1000) {
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
                            } else if (response.body().getStatus() == 1000) {
                                openSheet3();
                                //  Log.e("kdkdk", response.code() + "");
                                //  Toast.makeText(SignUpActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                            }


                        } else {


                            switch (response.code()) {
                                case 1000:
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
                                case 1000:
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
        List<ItemCartModel> productDetailsList;
        CreateOrderModel add_order_model = preferences.getcart_olivaData(HomeActivity.this);
        // productDetailsList = new ArrayList<>();
        if (add_order_model != null) {
            productDetailsList = add_order_model.getDetails();
            if (productDetailsList == null) {
                productDetailsList = new ArrayList<>();
            }
        } else {
            // Log.e(";;;;","kkxxdkjdjjdj");
            add_order_model = new CreateOrderModel();
            productDetailsList = new ArrayList<>();
        }
        int pos = -1;

        for (int i = 0; i < productDetailsList.size(); i++) {
            //  Log.e("droooee", productDetailsList.get(i).getProduct_id() + "  " + productModel.getId());
            if (productDetailsList.get(i).getProduct_id() == productModel.getId()) {
                pos = i;
                break;
            }
        }

        if (!categoryindex.contains(productModel.getCategory_id())) {

            if (productModel.getFirst_stock() != null && productModel.getFirst_stock().getQty() > 0) {
                if (pos == -1) {
                    ItemCartModel productDetails = new ItemCartModel();
                    productDetails.setQty(1);
                    productDetails.setImage(productModel.getImageBitmap());
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
                    //  productDetails.setProducts_id(productids);
                    productDetailsList.add(productDetails);
                    add_order_model.setDetails(productDetailsList);
                    productModel.setCount(1);
                    productModel.getFirst_stock().setQty(productModel.getFirst_stock().getQty() - 1);

                } else {
                    ItemCartModel productDetails = productDetailsList.get(pos);
                    productDetails.setQty(1 + productDetails.getQty());
                    productDetails.setNet_unit_price(productModel.getPrice());
                    if (productModel.getTax() != null) {
                        productDetails.setSubtotal((((productModel.getPrice() * productModel.getTax().getRate()) / 100) + productModel.getPrice()) * productDetails.getQty());

                    } else {
                        productDetails.setSubtotal(productModel.getPrice() * productDetails.getQty());

                    }
                    productDetailsList.remove(pos);
                    productDetailsList.add(pos, productDetails);

                    add_order_model.setDetails(productDetailsList);
                    productModel.setCount(productDetails.getQty());
                    productModel.getFirst_stock().setQty(productModel.getFirst_stock().getQty() - 1);
                }
                productModelList.set(layoutPosition, productModel);
                productAdapter.notifyDataSetChanged();
                accessDatabase.udatefirststock(productModel.getFirst_stock(), this);
                accessDatabase.udateproduct(productModel, this);

            } else {
                Toast.makeText(HomeActivity.this, getResources().getString(R.string.unvailable), Toast.LENGTH_LONG).show();
            }
        } else {
            if (categoryindex.contains(productModel.getCategory_id()) && productModel.getCategory_id() != 8) {
                if (productModel.getCan_make() > 0) {
                    if (pos == -1) {
                        ItemCartModel productDetails = new ItemCartModel();
                        productDetails.setQty(1);
                        productDetails.setImage(productModel.getImageBitmap());
                        productDetails.setName(productModel.getName());
                        productDetails.setNet_unit_price(productModel.getPrice());
                        productDetails.setProduct_id(productModel.getId());
                        productDetails.setProduct_batch_id("");
                        productDetails.setProduct_code(productModel.getCode());
                        productDetails.setDiscount(0);
                        productDetails.setStock((int) productModel.getCan_make());

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
                        //productDetails.setProducts_id(productids);
                        productDetailsList.add(productDetails);
                        add_order_model.setDetails(productDetailsList);
                        productModel.setCount(1);
                        productModel.setCan_make(productModel.getCan_make() - 1);

                    } else {
                        ItemCartModel productDetails = productDetailsList.get(pos);
                        productDetails.setQty(1 + productDetails.getQty());
                        productDetails.setNet_unit_price(productModel.getPrice());
                        if (productModel.getTax() != null) {
                            productDetails.setSubtotal((((productModel.getPrice() * productModel.getTax().getRate()) / 100) + productModel.getPrice()) * productDetails.getQty());

                        } else {
                            productDetails.setSubtotal(productModel.getPrice() * productDetails.getQty());

                        }
                        productDetailsList.remove(pos);
                        productDetailsList.add(pos, productDetails);

                        add_order_model.setDetails(productDetailsList);
                        productModel.setCount(productDetails.getQty());
                        productModel.setCan_make(productModel.getCan_make() - 1);
                    }
                    productModelList.set(layoutPosition, productModel);
                    productAdapter.notifyDataSetChanged();
                    //accessDatabase.udatefirststock(productModel.getFirst_stock(), this);
                    accessDatabase.udateproduct(productModel, this);

                } else {
                    Toast.makeText(HomeActivity.this, getResources().getString(R.string.unvailable), Toast.LENGTH_LONG).show();
                }
            } else {
                productModels.clear();
                productindex.clear();
                // Log.e(";lll", productModel.getOffer_products().size() + "");
                if (productModel.getOffer_products() != null && productModel.getOffer_products().size() > 0) {
                    int can = 0;
                    for (int i = 0; i < productModel.getOffer_products().size(); i++) {
                        for (int j = 0; j < allproduct.size(); j++) {
                            Log.e("sss", allproduct.get(j).getId() + " " + productModel.getOffer_products().get(i).getProduct_id());
                            if (allproduct.get(j).getId() == productModel.getOffer_products().get(i).getProduct_id()) {
                                productModels.add(allproduct.get(j));
                                productindex.add(j);

                            }
                        }
                    }
                    //   Log.e("lslslls", productModels.size() + " " + productModel.getOffer_products().size());

                    if (productModels.size() == productModel.getOffer_products().size()) {
                        for (int i = 0; i < productModels.size(); i++) {
                            ProductModel productModel1 = productModels.get(i);
                            if (!categoryindex.contains(productModel1.getCategory_id())) {

                                if (productModel1.getFirst_stock() != null && productModel1.getFirst_stock().getQty() > 0) {
                                    productModel1.getFirst_stock().setQty(productModel1.getFirst_stock().getQty() - 1);
                                    productModels.set(i, productModel1);
                                } else {
                                    can = -1;
                                }
                            } else {
                                if (categoryindex.contains(productModel1.getCategory_id()) && productModel1.getCategory_id() != 8) {
                                    if (productModel1.getCan_make() > 0) {
                                        productModel1.setCan_make(productModel1.getCan_make() - 1);
                                        productModels.set(i, productModel1);

                                    } else {
                                        can = -1;
                                    }
                                }
                            }
                        }
                        Log.e("s;;s;", can + "");
                        if (can != -1) {
                            if (pos == -1) {
                                ItemCartModel productDetails = new ItemCartModel();
                                productDetails.setQty(1);
                                productDetails.setImage(productModel.getImageBitmap());
                                productDetails.setName(productModel.getName());
                                productDetails.setNet_unit_price(productModel.getPrice());
                                productDetails.setProduct_id(productModel.getId());
                                productDetails.setProduct_batch_id("");
                                productDetails.setProduct_code(productModel.getCode());
                                productDetails.setDiscount(0);
                                productDetails.setStock((int) productModel.getCan_make());

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
                                //productDetails.setProducts_id(productids);
                                productDetailsList.add(productDetails);
                                add_order_model.setDetails(productDetailsList);
                                productModel.setCount(1);

                            } else {
                                ItemCartModel productDetails = productDetailsList.get(pos);
                                productDetails.setQty(1 + productDetails.getQty());
                                productDetails.setNet_unit_price(productModel.getPrice());
                                if (productModel.getTax() != null) {
                                    productDetails.setSubtotal((((productModel.getPrice() * productModel.getTax().getRate()) / 100) + productModel.getPrice()) * productDetails.getQty());

                                } else {
                                    productDetails.setSubtotal(productModel.getPrice() * productDetails.getQty());

                                }
                                productDetailsList.remove(pos);
                                productDetailsList.add(pos, productDetails);

                                add_order_model.setDetails(productDetailsList);
                                productModel.setCount(productDetails.getQty());
                            }
                            productModelList.set(layoutPosition, productModel);

                            //accessDatabase.udatefirststock(productModel.getFirst_stock(), this);
                            accessDatabase.udateproduct(productModel, this);
                            for (int i = 0; i < productModels.size(); i++) {
                                allproduct.set(productindex.get(i), productModels.get(i));
                                if (!categoryindex.contains(productModels.get(i).getCategory_id())) {
                                    accessDatabase.udatefirststock(productModels.get(i).getFirst_stock(), this);
                                }
                                //
                                accessDatabase.udateproduct(productModels.get(i), this);
                            }
                            productAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(HomeActivity.this, getResources().getString(R.string.unvailable), Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Toast.makeText(HomeActivity.this, getResources().getString(R.string.unvailable), Toast.LENGTH_LONG).show();

                    }

                } else {
                    Toast.makeText(HomeActivity.this, getResources().getString(R.string.unvailable), Toast.LENGTH_LONG).show();
                }
            }
        }
        if (productDetailsList.size() > 0) {
            preferences.create_update_cart_oliva(HomeActivity.this, add_order_model);
            getCartItemCount();
        } else {
            preferences.clearcart_oliva(this);
            getCartItemCount();
        }

    }

    @Override
    public void onRetrieveDataSuccess(long bol) {
        Log.e("productindex", productinsert + "" + bol);
        if (bol > 0) {
            productinsert += 1;
            if (productinsert < productModelList1.size()) {
                // productinsertboolen = false;
                inserttax();


            } else if (taxinsert < productModelList1.size()) {
                inserttax();
            }
        } else {
            insertproduct();
        }
        //   Log.e("dldlldld", productinsert + "");


//        for (int i = 0; i < productModelList1.size(); i++) {
//            if (productModelList1.get(i).getFirst_stock() != null) {
//                accessDatabase.insertFirst(productModelList1.get(i).getFirst_stock(), this);
//            }
//            if (productModelList1.get(i).getUnit() != null) {
//                productModelList1.get(i).getUnit().setProduct_id(productModelList1.get(i).getId());
//                accessDatabase.insertUnit(productModelList1.get(i).getUnit(), this);
//
//            }
//            if (productModelList1.get(i).getTax() != null) {
//                productModelList1.get(i).getTax().setProduct_id(productModelList1.get(i).getId());
//
//                accessDatabase.insertTax(productModelList1.get(i).getTax(), this);
//
//            }
//            if (productModelList1.get(i).getOffer_products() != null) {
//                Log.e("insertoffer", productModelList1.get(i).getOffer_products().size() + "");
//                accessDatabase.insertOffer(productModelList1.get(i).getOffer_products(), HomeActivity.this);
//
//            }
//        }
        //  accessDatabase.getProduct(HomeActivity.this, "1", "featured");

    }

    private void inserttax() {
        Log.e("taxinsert", taxinsert + "");
        if (productModelList1.get(taxinsert).getTax() != null) {
            productModelList1.get(taxinsert).getTax().setProduct_id(productModelList1.get(taxinsert).getId());
            accessDatabase.insertTax(productModelList1.get(taxinsert).getTax(), this);
        } else {
            taxinsert += 1;
            if (taxinsert < productModelList1.size()) {
                //taxboolen = false;
                insertunit();
            } else if (unitinsert < productModelList1.size()) {
                insertunit();
            }
//            else {
//                inserttax();
//            }
        }
    }

    private void insertunit() {
        Log.e("unit", unitinsert + "");

        if (productModelList1.get(unitinsert).getUnit() != null) {
            productModelList1.get(unitinsert).getUnit().setProduct_id(productModelList1.get(unitinsert).getId());
            accessDatabase.insertUnit(productModelList1.get(unitinsert).getUnit(), this);
        } else {
            unitinsert += 1;
            if (unitinsert < productModelList1.size()) {
                // unitbolean = false;
                insertfirststock();
            } else if (firststockinsert < productModelList1.size()) {
                insertfirststock();
            }
//            else {
//                insertunit();
//            }
        }
    }

    private void insertfirststock() {
        Log.e("first", firststockinsert + "");
        if (productModelList1.get(firststockinsert).getFirst_stock() != null) {
            accessDatabase.insertFirst(productModelList1.get(firststockinsert).getFirst_stock(), this);
        } else {
            firststockinsert += 1;
            if (firststockinsert < productModelList1.size()) {
                // firststockboolean = false;
                Log.e(";slslsl", offerinsert + " " + firststockinsert);
                insertoffer();
            } else if (offerinsert < productModelList1.size()) {
                insertoffer();
            }
//            else {
//                insertfirststock();
//            }
        }
    }

    private void insertoffer() {
        Log.e("offerinsert", offerinsert + "");
        if (productModelList1.get(offerinsert).getOffer_products() != null && productModelList1.get(offerinsert).getOffer_products().size() > 0) {
            Log.e("offferrs", productModelList1.get(offerinsert).getOffer_products().size() + "");
            accessDatabase.insertOffer(productModelList1.get(offerinsert).getOffer_products(), this);
        } else {
            offerinsert += 1;
            if (offerinsert == productModelList1.size()) {
                //  offerboolean = false;
                progressDialog.dismiss();
                searchtype = "featured";
                id = "1";
                productModelList.clear();
                accessDatabase.getallProduct(this);
                //  accessDatabase.getProduct(HomeActivity.this, "1", "featured", 10, 0);
            } else {
                insertproduct();
            }
        }
    }

    @Override
    public void onCategoryDataInsertedSuccess(long bol) {
        Log.e("sssss", bol + " " + categoryinsert);
        if (bol > 0) {
            categoryinsert += 1;

        }
        if (categoryinsert == categoryModelList.size()) {

            accessDatabase.getCategory(HomeActivity.this);

        } else {
            insertcategory();
        }
    }

    //
    @Override
    public void onCategoryDataSuccess(List<CategoryModel> categoryModelList) {

        this.categoryModelList.clear();
        this.categoryModelList.addAll(categoryModelList);

        homeAdapter.notifyDataSetChanged();

    }

    public void setImageBitmap(String basurl, CategoryModel categoryModel, int pos1) {
//        Picasso.get().load(basurl).into(new Target() {
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//
//                bitmapimage = bitmap;
//                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                bitmapimage.compress(Bitmap.CompressFormat.PNG, 10, outputStream);
//                categoryModel.setImageBitmap(outputStream.toByteArray());
//
//
//                if (pos1 < categoryModelList.size()) {
//                    categoryModelList.set(pos1, categoryModel);
//                }
////
//                Log.e("dssss", pos1 + "");
//
//                accessDatabase.insertCategory(categoryModel, HomeActivity.this);
////                if (pos1 == categoryModelList.size() - 1) {
////                    accessDatabase.getCategory(HomeActivity.this);
////                }
//                //  Toast.makeText(getApplicationContext(), "Image Downloaded", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//                accessDatabase.insertCategory(categoryModel, HomeActivity.this);
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
                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {

                        bitmapimage = bitmap;
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        bitmapimage.compress(Bitmap.CompressFormat.PNG, 10, outputStream);
                        categoryModel.setImageBitmap(outputStream.toByteArray());


                        if (pos1 < categoryModelList.size()) {
                            categoryModelList.set(pos1, categoryModel);
                        }
//
                        Log.e("dssss", pos1 + "");

                        accessDatabase.insertCategory(categoryModel, HomeActivity.this);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
//                        super.onLoadFailed(errorDrawable);
                        accessDatabase.insertCategory(categoryModel, HomeActivity.this);

                    }
                });
    }

    public void setImageBitmap(String basurl, ProductModel productModel, int pos1) {

//        Picasso.get().load(basurl).into(new Target() {
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//
//                try {
//                    bitmapimage = bitmap;
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    bitmapimage.compress(Bitmap.CompressFormat.PNG, 10, stream);
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

                        bitmapimage = resource;
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmapimage.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                        productModel.setImageBitmap(stream.toByteArray());
                        if (productModelList1.size() > 0 && pos1 < productModelList1.size()) {
                            productModelList1.set(pos1, productModel);
                        }

                        accessDatabase.insertRetrieve(productModel, HomeActivity.this);

//                        if (pos1 == productModelList1.size() - 1) {
//                            progressDialog.dismiss();
//                            searchtype = "featured";
//                            id = "1";
//                            productModelList1.clear();
//
//                            accessDatabase.getProduct(HomeActivity.this, "1", "featured", 10, 0);
//                        }

                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        // super.onLoadFailed(errorDrawable);
                        accessDatabase.insertRetrieve(productModel, HomeActivity.this);

                    }
                });
    }

    public void setImageBitmap(String basurl, BrandModel brandModel, int pos) {

//        Picasso.get().load(basurl).into(new Target() {
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//
//                try {
//                    bitmapimage = bitmap;
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    bitmapimage.compress(Bitmap.CompressFormat.PNG, 10, stream);
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

                        bitmapimage = resource;
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmapimage.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                        brandModel.setImageBitmap(stream.toByteArray());
                        brandModelList.set(pos, brandModel);
                        accessDatabase.insertBrand(brandModel, HomeActivity.this);
                        if (pos == brandModelList.size() - 1) {
                            accessDatabase.getBrand(HomeActivity.this);
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }

    @Override
    public void onProductDataSuccess(List<ProductModel> productModelList) {

        HomeActivity.this.productModelList.addAll(productModelList);
        layoutpos = 0;
        if (productModelList.size() > 0) {
            accessDatabase.getTax(HomeActivity.this, HomeActivity.this.productModelList.get(layoutpos).getId());
        }


        //  layoutpos = -1;


    }

    @Override
    public void onFirstStockDataSuccess(Long bol) {
        if (bol > 0) {
            firststockinsert += 1;
            if (firststockinsert < productModelList1.size()) {
                // firststockboolean = false;
                Log.e(";slslsl", offerinsert + " " + firststockinsert);
                insertoffer();
            } else if (offerinsert < productModelList1.size()) {
                insertoffer();
            }
        } else {
            insertfirststock();
        }
    }

    @Override
    public void onTaxDataSuccess(long bol) {
        if (bol > 0) {
            taxinsert += 1;

            if (taxinsert < productModelList1.size()) {
                // taxboolen = false;
                insertunit();
            } else if (unitinsert < productModelList1.size()) {
                insertunit();
            }
        } else {
            inserttax();
        }
//        else {
//            inserttax();
//        }
    }

    @Override
    public void onUnitDataSuccess(long bol) {
        if (bol > 0) {
            unitinsert += 1;
            if (unitinsert < productModelList1.size()) {
                //unitbolean = false;
                insertfirststock();
                // insertunit();
            } else if (firststockinsert < productModelList1.size()) {
                insertfirststock();
            }
        } else {
            insertunit();
        }
//        else {
//
//        }
    }

    @Override
    public void onOfferDataInsertedSuccess(boolean bol) {
        Log.e("d;ldldl", bol + "" + productModelList1.size());
        if (bol) {
            offerinsert += 1;
            if (offerinsert == productModelList1.size()) {

                progressDialog.dismiss();
                searchtype = "featured";
                id = "1";
                productModelList.clear();
                accessDatabase.getProduct(HomeActivity.this, "1", "featured", 10, 0);
            } else {
                //insertoffer();
                insertproduct();
            }
        } else {
            insertoffer();
        }

    }

    @Override
    public void onBrandDataInsertedSuccess(long bol) {
        if (bol > 0) {
            brandinsert += 1;
        }
        Log.e("dlldl", brandinsert + "");
        if (brandinsert == brandModelList.size()) {
            // brandisertboolean = false;
            accessDatabase.getBrand(this);
        } else {
            insertbrand();
        }
    }

    public void getOfflineProdusts(String brand_id, String cat_id, String isfeatured) {
        searchtype = "featured";
        id = isfeatured;
        productModelList.clear();

        accessDatabase.getProduct(HomeActivity.this, isfeatured, "featured", 10, 0);

    }

    @Override
    public void onBrandDataSuccess(List<BrandModel> brandModelList) {
        this.brandModelList.clear();
        this.brandModelList.addAll(brandModelList);
        markAdapter.notifyDataSetChanged();

    }

    @Override
    public void onTaxDataSuccess(ProductModel.Tax tax) {
        if (tax != null) {
            if (getall) {
                ProductModel productModel = productModelList.get(layoutpos);
                productModel.setTax(tax);
                productModelList.set(layoutpos, productModel);
            } else {
                ProductModel productModel = allproduct.get(layoutpos2);
                productModel.setTax(tax);
                allproduct.set(layoutpos2, productModel);
            }
        }
        if (getall) {
            accessDatabase.getFirstStock(HomeActivity.this, HomeActivity.this.productModelList.get(layoutpos).getId());
        } else {
            accessDatabase.getFirstStock(HomeActivity.this, HomeActivity.this.allproduct.get(layoutpos2).getId());

        }

//        if (layoutpos != -1 && this.productModelList.size() > layoutpos) {
//            ProductModel productModel = this.productModelList.get(layoutpos);
//            productModel.setTax(productModelList);
//            this.productModelList.set(layoutpos, productModel);
//        } else if (layoutpos2 != -1 && allproduct.size() > layoutpos2) {
//            ProductModel productModel = this.allproduct.get(layoutpos2);
//            productModel.setTax(productModelList);
//            this.allproduct.set(layoutpos2, productModel);
//        }
    }

    @Override
    public void onFirstStockDataSuccess(ProductModel.FirstStock firstStock) {
        if (firstStock != null) {
            if (getall) {
                ProductModel productModel = productModelList.get(layoutpos);
                productModel.setFirst_stock(firstStock);
                productModelList.set(layoutpos, productModel);
            } else {
                ProductModel productModel = allproduct.get(layoutpos2);
                productModel.setFirst_stock(firstStock);
                allproduct.set(layoutpos2, productModel);
            }
        }
        if (getall) {
            accessDatabase.getUnit(HomeActivity.this, HomeActivity.this.productModelList.get(layoutpos).getId());
        } else {
            accessDatabase.getUnit(HomeActivity.this, HomeActivity.this.allproduct.get(layoutpos2).getId());

        }
        //        if (layoutpos != -1 && this.productModelList.size() > layoutpos) {
//            ProductModel productModel = this.productModelList.get(layoutpos);
//            productModel.setFirst_stock(productModelList);
//            this.productModelList.set(layoutpos, productModel);
//        }
//        else if (layoutpos2 != -1 && allproduct.size() > layoutpos2) {
//            ProductModel productModel = this.allproduct.get(layoutpos2);
//            productModel.setFirst_stock(productModelList);
//            this.allproduct.set(layoutpos2, productModel);
//        }

    }

    @Override
    public void onUnitDataSuccess(ProductModel.Unit unit) {
        if (unit != null) {
            if (getall) {
                ProductModel productModel = productModelList.get(layoutpos);
                productModel.setUnit(unit);
                productModelList.set(layoutpos, productModel);
            } else {
                ProductModel productModel = allproduct.get(layoutpos2);
                productModel.setUnit(unit);
                allproduct.set(layoutpos2, productModel);
            }
        }
        if (getall) {
            accessDatabase.getOffersProduct(HomeActivity.this, HomeActivity.this.productModelList.get(layoutpos).getId() + "");
        } else {
            layoutpos2 += 1;
            if (layoutpos2 < allproduct.size()) {
                accessDatabase.getTax(this, allproduct.get(layoutpos2).getId());

            } else {
                getall = true;
                accessDatabase.getProduct(this, "1", "featured", 10, 0);
                layoutpos = 0;
            }
        }
//        if (layoutpos != -1 && this.productModelList.size() > layoutpos) {
//
//        } else if (layoutpos2 != -1 && allproduct.size() > layoutpos2) {
//            ProductModel productModel = this.allproduct.get(layoutpos2);
//            productModel.setUnit(productModelList);
//            this.allproduct.set(layoutpos2, productModel);
//        }
    }

    @Override
    public void onProductOffersDataSuccess(List<ProductModel.OfferProducts> productModelList) {
        Log.e("ddlkdkdk", productModelList.size() + "");
        if (productModelList != null) {
            ProductModel productModel = this.productModelList.get(layoutpos);
            productModel.setOffer_products(productModelList);
            this.productModelList.set(layoutpos, productModel);
        }
        layoutpos += 1;
        if (layoutpos < productModelList.size()) {
            accessDatabase.getTax(HomeActivity.this, HomeActivity.this.productModelList.get(layoutpos).getId());

        } else {
            productAdapter.notifyDataSetChanged();
        }
//        if (layoutpos != -1 && this.productModelList.size() > layoutpos) {
//            ProductModel productModel = this.productModelList.get(layoutpos);
//            productModel.setOffer_products(productModelList);
//            this.productModelList.set(layoutpos, productModel);
//            layoutpos += 1;
//        } else if (layoutpos2 != -1 && allproduct.size() > layoutpos2) {
//            ProductModel productModel = this.allproduct.get(layoutpos2);
//            productModel.setOffer_products(productModelList);
//            this.allproduct.set(layoutpos2, productModel);
//            layoutpos2 += 1;
//        }
    }

    @Override
    public void onFirstStockUpdateSuccess() {


//        else {
//            insertfirststock();
//        }


    }

    @Override
    public void onproductUpdateSuccess() {

    }

    @Override
    public void onAllProductDataSuccess(List<ProductModel> productModelList) {
        allproduct.clear();
        allproduct.addAll(productModelList);
//Log.e("d;;d;d;",allproduct.size()+"");
        layoutpos2 = 0;
        if (allproduct.size() > 0) {
            accessDatabase.getTax(this, allproduct.get(layoutpos2).getId());
        }
    }

    private void getCustomerGroup() {

        // customerGroupModelList.clear();
        Api.getService(Tags.base_url)
                .getCustomerGroup()
                .enqueue(new Callback<CustomerGroupDataModel>() {
                    @Override
                    public void onResponse(Call<CustomerGroupDataModel> call, Response<CustomerGroupDataModel> response) {
                        // dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                if (response.body().getData() != null) {
                                    if (response.body().getData().size() > 0) {
                                        //  customerGroupModelList.add(new CustomerGroupModel(getResources().getString(R.string.choose_customer_group)));
                                        //customerGroupModelList.addAll(response.body().getData());
                                        //spinnerCustomerGroupAdapter.notifyDataSetChanged();
                                        accessDatabase.insertCustomerGroup(response.body().getData(), HomeActivity.this);

                                    } else {

                                    }
                                }
                            } else {
                                Log.e("kdkdk", response.code() + "");
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
                    public void onFailure(Call<CustomerGroupDataModel> call, Throwable t) {
                        try {
                            //   dialog.dismiss();
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

    private void gettax() {

        Api.getService(Tags.base_url)
                .getTax()
                .enqueue(new Callback<TaxDataModel>() {
                    @Override
                    public void onResponse(Call<TaxDataModel> call, Response<TaxDataModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                if (response.body().getData() != null) {
                                    if (response.body().getData().size() > 0) {
                                        accessDatabase.insertMainTax(response.body().getData(), HomeActivity.this);
                                    } else {

                                    }
                                }
                            } else {
                                Log.e("kdkdk", response.code() + "");
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
                    public void onFailure(Call<TaxDataModel> call, Throwable t) {
                        try {

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

    private void getCustomer() {


        Api.getService(Tags.base_url)
                .getCustomer()
                .enqueue(new Callback<CustomerDataModel>() {
                    @Override
                    public void onResponse(Call<CustomerDataModel> call, Response<CustomerDataModel> response) {
                        //dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                if (response.body().getData() != null) {
                                    if (response.body().getData().size() > 0) {
                                        accessDatabase.insertCustomer(response.body().getData(), HomeActivity.this);

                                    } else {

                                    }
                                }
                            } else {
                                Log.e("kdkdk", response.code() + "");
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
                    public void onFailure(Call<CustomerDataModel> call, Throwable t) {
                        try {
                            //  dialog.dismiss();
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

    @Override
    public void onCustomerGroupInsertedSuccess(boolean bol) {
        if (bol) {
            getCustomer();
        }
    }

    @Override
    public void onMainTaxInsertedSuccess(boolean bol) {

    }

    @Override
    public void onCustomerDataInsertedSuccess(boolean bol) {

    }
}
