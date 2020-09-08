package com.alp.familymart.modelclass;

import com.alp.familymart.modelclass.product_model.ProductModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class   ViewCartModel implements Serializable {


//    public List<ProductModel> items;
//    //public String item_count;
//    //public String currency;
//
//
//    public List<ProductModel> getItems() {
//        return items;
//    }
//
//    public void setItems(List<ProductModel> items) {
//        this.items = items;
//    }


    @SerializedName("product_name")
    @Expose
    private String product_name;

    @SerializedName("product_price")
    @Expose
    private String product_price;

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }
}
