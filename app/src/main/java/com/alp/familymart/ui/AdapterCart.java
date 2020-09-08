package com.alp.familymart.ui;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alp.familymart.R;
import com.alp.familymart.click_listeners.ProductClickListener;
import com.alp.familymart.modelclass.Example;
import com.alp.familymart.modelclass.ViewCartModel;
import com.alp.familymart.modelclass.product_model.Image;
import com.alp.familymart.modelclass.product_model.ProductModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class  AdapterCart extends RecyclerView.Adapter<AdapterCart.ViewHolder> {
    Context mContext;
    List<ProductModel> items;
    ProductClickListener productClickListener;
    Button remove;
    Button update;
    //EditText qunttity;
   // CartModel cartModel;

    public AdapterCart(Context context,  List<ProductModel> items, ProductClickListener productClickListener) {
        this.mContext = context;
        this.items = items;
        this.productClickListener = productClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_cart,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /*
        if(product.get(position).getName() != null)
            holder.tvProductName.setText(product.get(position).getName());
        holder.tvProductPrice.setText("\u20B9"+product.get(position).getPrice());

        holder.tvregularprice.setText("\u20B9"+product.get(position).getRegularPrice());

        Picasso.get().load(product.get(position).getImages().get(0).getSrc()).into(holder.productImage);
        */
        if(items.get(position).getProduct_name() != null)
        holder.tvProductName.setText(items.get(position).getProduct_name());
        holder.tvProductPrice.setText(items.get(position).getPrice());
        holder.tvregularprice.setText(items.get(position).getRegularPrice());
        if(items.get(position).getStockQuantity()!=null)
        holder.etQuantity.setText( items.get(position).getStockQuantity().toString());


       // if (items.get(position).getImages() != null && items.get(position).getImages().size() > 0)
         //  Picasso.get().load(items.get(position).getGallery().get(0).getSrc()).into(holder.productImage);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvProductName,tvProductPrice,tvregularprice ;
        ImageView productImage ;
        EditText etQuantity;

        public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvProductPrice = itemView.findViewById(R.id.tv_product_price);
            tvregularprice = itemView.findViewById(R.id.tv_regular_price);
            etQuantity = itemView.findViewById(R.id.et_quantity);
            productImage = itemView.findViewById(R.id.tv_product_image);
            remove = itemView.findViewById(R.id.btn_rmv);
            update = itemView.findViewById(R.id.btn_update_cart);
            final Integer quantity = Integer.parseInt(etQuantity.getText().toString());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productClickListener.onProductClicked(items.get(getAdapterPosition()), getAdapterPosition());

                }
            });

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productClickListener.onProductRemoved(items.get(getAdapterPosition()), getAdapterPosition());
                }
            });

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productClickListener.onProductUpdated(items.get(getAdapterPosition()), getAdapterPosition(), quantity);
                }
            });

        }
    }
}
