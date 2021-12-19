package com.oliva2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.oliva2.R;
import com.oliva2.databinding.ProductBillRowBinding;
import com.oliva2.models.InvoiceDataModel;

import java.util.List;

public class ProductBillAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<InvoiceDataModel.LimsProductSaleData> list;
    private Context context;
    private LayoutInflater inflater;
    private int pos = -1;

    //private Fragment_Main fragment_main;
    public ProductBillAdapter(List<InvoiceDataModel.LimsProductSaleData> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        //  this.fragment_main=fragment_main;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        ProductBillRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.product_bill_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
if(position==list.size()-1){
    myHolder.binding.view.setVisibility(View.GONE);
}
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public ProductBillRowBinding binding;

        public MyHolder(@NonNull ProductBillRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
