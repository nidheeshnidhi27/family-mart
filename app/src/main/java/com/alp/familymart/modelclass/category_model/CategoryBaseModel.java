package com.alp.familymart.modelclass.category_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CategoryBaseModel implements Serializable {

    @SerializedName("category")
    @Expose
    public Category category;

}
