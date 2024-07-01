package com.example.mactrackifyalpha.Entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransactionEntity {

    private String id;
    private String uuid;
    private String status;
    private AccountEntity account;
    private UserEntity user;
    @SerializedName("items")
    private List<ItemsEntity> itemsEntities;

    public TransactionEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<ItemsEntity> getItemsEntities() {
        return itemsEntities;
    }

    public void setItemsEntities(List<ItemsEntity> itemsEntities) {
        this.itemsEntities = itemsEntities;
    }
}
