package com.oliva2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.oliva2.R;
import com.oliva2.activities_fragments.activity_cart.CartActivity;
import com.oliva2.databinding.CartRowBinding;
import com.oliva2.models.ItemCartModel;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ItemCartModel> list;
    private Context context;
    private LayoutInflater inflater;

    //private Fragment_Main fragment_main;
    public CartAdapter(List<ItemCartModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        //  this.fragment_main=fragment_main;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        CartRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.cart_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));


//Log.e("eeee",list.get(position).getOffer_value()+""+(list.get(position).getAmount()%list.get(position).getOffer_min()));
        // Log.e("ssss",((list.get(position).getHave_offer().equals("yes")?(list.get(position).getOffer_type().equals("per")?(list.get(position).getProduct_default_price().getPrice()-((list.get(position).getProduct_default_price().getPrice()*list.get(position).getOffer_value())/100)):list.get(position).getProduct_default_price().getPrice()-list.get(position).getOffer_value()):list.get(position).getProduct_default_price().getPrice())+""));
        myHolder.binding.imageIncrease.setOnClickListener(v -> {
            ItemCartModel model2 = list.get(myHolder.getAdapterPosition());
            if(context instanceof CartActivity){
                CartActivity activity=(CartActivity) context;
                int amount = model2.getQty() + 1;
                model2.setQty(amount);
                activity.increase_decrease(model2, myHolder.getAdapterPosition());
            }});

        myHolder.binding.imageDecrease.setOnClickListener(v -> {
            ItemCartModel model2 = list.get(myHolder.getAdapterPosition());
            if(context instanceof CartActivity){
                CartActivity activity=(CartActivity) context;
                int amount = model2.getQty() - 1;
                if(amount==0){
                    activity.deleteItem(model2, myHolder.getAdapterPosition());

                }
                else {
                    model2.setQty(amount);
                    activity.increase_decrease(model2, myHolder.getAdapterPosition());
                }

            }});

        myHolder.binding.imgRemove.setOnClickListener(v -> {
            if(context instanceof CartActivity){
                CartActivity activity=(CartActivity) context;
                ItemCartModel model2 = list.get(myHolder.getAdapterPosition());
                activity.deleteItem(model2, myHolder.getAdapterPosition());
            }});


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public CartRowBinding binding;

        public MyHolder(@NonNull CartRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
