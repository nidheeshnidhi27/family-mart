package com.alp.familymart.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alp.familymart.R;
import com.alp.familymart.click_listeners.ProductClickListener;
import com.alp.familymart.modelclass.LineItem;
import com.alp.familymart.modelclass.product_model.ProductModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterProductsList extends RecyclerView.Adapter<AdapterProductsList.ViewHolder> {
    Context mContext;
    List<LineItem> products;
    List<ProductModel> example;
    ProductClickListener productClickListener;
   // RecyclerView rc_drawer;

    public AdapterProductsList(Context context, List<ProductModel> example, ProductClickListener productClickListener) {
        this.mContext = context;
        this.example = example;
        this.productClickListener = productClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_products_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(example.get(position).getName() != null)
            holder.tvProductName.setText(example.get(position).getName());
        holder.tvProductPrice.setText("\u20B9"+example.get(position).getPrice());

        holder.tvregularprice.setText("\u20B9"+example.get(position).getRegularPrice());

        Picasso.get().load(example.get(position).getImages().get(0).getSrc()).into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return example.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvProductName,tvProductPrice,tvregularprice ;
        ImageView productImage = itemView.findViewById(R.id.tv_product_image);

        public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvProductPrice = itemView.findViewById(R.id.tv_product_price);
            tvregularprice = itemView.findViewById(R.id.tv_regular_price);

          //  rc_drawer = itemView.findViewById(R.id.rc_drawer);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productClickListener.onProductClicked(example.get(getAdapterPosition()),getAdapterPosition());
                }
            });

        }
    }
}
