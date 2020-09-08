package com.alp.familymart.click_listeners;

import com.alp.familymart.modelclass.cart_model.ProductView;
import com.alp.familymart.modelclass.product_model.ProductModel;

public interface ProductClickListener {

    public void onProductClicked(ProductModel example, int adapterPosition);
    public void onProductRemoved(ProductModel example,int adapterPosition);
    public void onProductUpdated(ProductModel example,int adapterPosition, int quantity);
}
