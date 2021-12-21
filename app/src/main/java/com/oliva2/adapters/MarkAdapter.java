package com.oliva2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.oliva2.R;
import com.oliva2.activities_fragments.activity_home.HomeActivity;
import com.oliva2.databinding.MarkRowBinding;
import com.oliva2.models.BrandModel;

import java.util.List;

public class MarkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BrandModel> list;
    private Context context;
    private LayoutInflater inflater;

    //private Fragment_Main fragment_main;
    public MarkAdapter(List<BrandModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        //  this.fragment_main=fragment_main;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        MarkRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.mark_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;

        myHolder.binding.setModel(list.get(position));

myHolder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(context instanceof HomeActivity){
            HomeActivity activity=(HomeActivity) context;
            activity.showBrand(list.get(holder.getLayoutPosition()).getId()+"");
        }
    }
});
//Log.e("eeee",list.get(position).getOffer_value()+""+(list.get(position).getAmount()%list.get(position).getOffer_min()));
        // Log.e("ssss",((list.get(position).getHave_offer().equals("yes")?(list.get(position).getOffer_type().equals("per")?(list.get(position).getProduct_default_price().getPrice()-((list.get(position).getProduct_default_price().getPrice()*list.get(position).getOffer_value())/100)):list.get(position).getProduct_default_price().getPrice()-list.get(position).getOffer_value()):list.get(position).getProduct_default_price().getPrice())+""));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public MarkRowBinding binding;

        public MyHolder(@NonNull MarkRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
