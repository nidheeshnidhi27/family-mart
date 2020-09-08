package com.alp.familymart.modelclass.cart_model;

import com.alp.familymart.modelclass.ViewCartModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;

public class ViewCartBaseModel implements Serializable {

   // private HashMap<String ,ViewCartModel> map;
    @SerializedName("5e9f92a01c986bafcabbafd145520b13")
    @Expose
    private ViewCartModel cart;

}
