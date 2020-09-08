package com.alp.familymart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alp.familymart.click_listeners.CategoryClickListener;
import com.alp.familymart.modelclass.category_model.ProductCategory;
import com.alp.familymart.modelclass.product_model.Category;
import com.alp.familymart.modelclass.product_model.ProductModel;
import com.alp.familymart.ui.AdapterProductsList;

import java.util.List;

public class AdapterDrawer extends RecyclerView.Adapter<AdapterDrawer.ViewHolder>{

    Context mContext;
    List<ProductCategory> cat_menu;
    CategoryClickListener categoryClickListener;

    public AdapterDrawer(Context context, List<ProductCategory> parsedResponse,CategoryClickListener categoryClickListener) {
        mContext=context;
        this.cat_menu = parsedResponse;
        this.categoryClickListener = categoryClickListener;
    }

    //ProductClickListener productClickListener;
    @NonNull
    @Override
    public AdapterDrawer.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_drawer,parent,false);
        return new AdapterDrawer.ViewHolder(view);
        //return null;
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterDrawer.ViewHolder holder, int position) {


        holder.tvItem.setText(cat_menu.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return cat_menu.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryClickListener.onCategoryClicked(cat_menu.get(getAdapterPosition()),getAdapterPosition());
                }
            });
        }
    }



}
