package com.oliva2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.oliva2.R;
import com.oliva2.activities_fragments.activity_home.HomeActivity;
import com.oliva2.databinding.HomeRowBinding;
import com.oliva2.models.CategoryModel;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CategoryModel> list;
    private Context context;
    private LayoutInflater inflater;

    //private Fragment_Main fragment_main;
    public HomeAdapter(List<CategoryModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        //  this.fragment_main=fragment_main;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        HomeRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.home_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        ((MyHolder) holder).binding.progBarCategory.setVisibility(View.GONE);

        CategoryAdapter categoryAdapter=new CategoryAdapter(list,context);
((MyHolder) holder).binding.recviewCategory.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
((MyHolder) holder).binding.recviewCategory.setAdapter(categoryAdapter);
if(list.size()==0){
    ((MyHolder) holder).binding.tvNoDataCategory.setVisibility(View.VISIBLE);
}
else {
    ((MyHolder) holder).binding.tvNoDataCategory.setVisibility(View.GONE);

}
        ((MyHolder) holder).binding.llTrasmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  categoryid="0";
                if(context instanceof HomeActivity){
                    HomeActivity activity=(HomeActivity)context;
                activity.openSheet();}
            }
        });
        ((MyHolder) holder).binding.llFeatured.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //categoryid="0";
                if(context instanceof HomeActivity){

                    HomeActivity activity=(HomeActivity)context;
                    activity.category_id=0;

                    activity.getOfflineProdusts("0", "0", "1");
            }}
        });
      //  myHolder.binding.setModel(list.get(position));

//Log.e("eeee",list.get(position).getOffer_value()+""+(list.get(position).getAmount()%list.get(position).getOffer_min()));
        // Log.e("ssss",((list.get(position).getHave_offer().equals("yes")?(list.get(position).getOffer_type().equals("per")?(list.get(position).getProduct_default_price().getPrice()-((list.get(position).getProduct_default_price().getPrice()*list.get(position).getOffer_value())/100)):list.get(position).getProduct_default_price().getPrice()-list.get(position).getOffer_value()):list.get(position).getProduct_default_price().getPrice())+""));


    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public HomeRowBinding binding;

        public MyHolder(@NonNull HomeRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
