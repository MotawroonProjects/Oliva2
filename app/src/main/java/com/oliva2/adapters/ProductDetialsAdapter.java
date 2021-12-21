package com.oliva2.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.oliva2.R;
import com.oliva2.activities_fragments.activity_home.HomeActivity;
import com.oliva2.databinding.ProductDetialsRowBinding;
import com.oliva2.models.ProductDetialsModel;

import java.util.List;

public class ProductDetialsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ProductDetialsModel> list;
    private Context context;
    private LayoutInflater inflater;
    private int pos = -1;

    //private Fragment_Main fragment_main;
    public ProductDetialsAdapter(List<ProductDetialsModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        //  this.fragment_main=fragment_main;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        ProductDetialsRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.product_detials_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        SpinnerProductAdapter spinnerProductAdapter = new SpinnerProductAdapter(list.get(position).getProductModelList(), context);

        myHolder.binding.spinnerProduct.setAdapter(spinnerProductAdapter);
        Log.e("selection", list.get(position).getSelection() + "");
        myHolder.binding.spinnerProduct.setSelection(list.get(position).getSelection());

        if (pos == position) {
            ((MyHolder) holder).binding.spinnerProduct.setSelection(0);
        }
//Log.e("eeee",list.get(position).getOffer_value()+""+(list.get(position).getAmount()%list.get(position).getOffer_min()));
        // Log.e("ssss",((list.get(position).getHave_offer().equals("yes")?(list.get(position).getOffer_type().equals("per")?(list.get(position).getProduct_default_price().getPrice()-((list.get(position).getProduct_default_price().getPrice()*list.get(position).getOffer_value())/100)):list.get(position).getProduct_default_price().getPrice()-list.get(position).getOffer_value()):list.get(position).getProduct_default_price().getPrice())+""));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public ProductDetialsRowBinding binding;

        public MyHolder(@NonNull ProductDetialsRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
