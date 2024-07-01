package com.example.mactrackifyalpha.Entities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AccountsEntity {
    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private ArrayList<AccountEntity> data;

    public AccountsEntity() {
    }

    public AccountsEntity(String message, ArrayList<AccountEntity> accounts) {
        this.message = message;
        this.data = accounts;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<AccountEntity> getData() {
        return data;
    }

    public void setData(ArrayList<AccountEntity> accounts) {
        this.data = accounts;
    }
}
