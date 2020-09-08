package com.alp.familymart.modelclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddCartModel  implements Serializable  {
    @SerializedName("product_name")
    @Expose
    private String product_name;

    @SerializedName("quantity")
    @Expose
    private String quantity;
    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
