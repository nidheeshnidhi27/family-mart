package com.alp.familymart.modelclass.cart_model;

import com.alp.familymart.modelclass.product_model.ProductModel;

import java.io.Serializable;
import java.util.List;

public class  CartModel implements Serializable {

    public List<ProductModel> items;
    //public String item_count;
    //public String currency;


    public List<ProductModel> getItems() {
        return items;
    }

    public void setItems(List<ProductModel> items) {
        this.items = items;
    }

//    public String getItem_count() {
//        return item_count;
//    }
//
//    public void setItem_count(String item_count) {
//        this.item_count = item_count;
//    }
//
//    public String getCurrency() {
//        return currency;
//    }
//
//    public void setCurrency(String currency) {
//        this.currency = currency;
//    }
}
