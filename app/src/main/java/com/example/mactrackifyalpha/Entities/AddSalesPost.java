package com.example.mactrackifyalpha.Entities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AddSalesPost {
    @SerializedName("account_name")
    private String accountName;
    @SerializedName("items")
    private ArrayList<AddDataFormEntity> items;

    public AddSalesPost() {
    }

    public AddSalesPost(String accountName, ArrayList<AddDataFormEntity> items) {
        this.accountName = accountName;
        this.items = items;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public ArrayList<AddDataFormEntity> getItems() {
        return items;
    }

    public void setItems(ArrayList<AddDataFormEntity> items) {
        this.items = items;
    }
}
