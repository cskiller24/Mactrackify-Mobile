package com.example.mactrackifyalpha.Entities;

import com.google.gson.annotations.SerializedName;

public class AddDataFormEntity {
    @SerializedName("product_name")
    private String productName;
    @SerializedName("quantity")
    private int quantity = 1;

    public AddDataFormEntity() {
    }

    public AddDataFormEntity(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
