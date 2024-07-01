package com.example.mactrackifyalpha.Entities;

import com.google.gson.annotations.SerializedName;

public class AccountEntity {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("number")
    private String number;
    @SerializedName("address")
    private String address;
    @SerializedName("balance")
    private String balance;

    public AccountEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
