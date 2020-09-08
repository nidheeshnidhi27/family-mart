package com.alp.familymart.modelclass;

import com.alp.familymart.modelclass.product_model.ProductModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BaseModel implements Serializable {
    @SerializedName("example")
    @Expose
    public List<ProductModel> example;

    public List<ProductModel> getExample() {
        return example;
    }

    public void setExample(List<ProductModel> example) {
        this.example = example;
    }
}
