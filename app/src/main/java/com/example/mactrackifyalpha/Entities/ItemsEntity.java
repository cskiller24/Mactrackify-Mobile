package com.example.mactrackifyalpha.Entities;

import com.google.gson.annotations.SerializedName;

public class ItemsEntity {
    @SerializedName("id")
    private String id;
    @SerializedName("product_name")
    private String productName;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("warehouse_name")
    private String warehouseName;
    @SerializedName("warehouse_description")
    private String warehouseDescription;
    @SerializedName("warehouse_price")
    private String warehousePrice;

    public ItemsEntity() {
    }

    public ItemsEntity(String id, String productName, int quantity, String warehouseName, String warehouseDescription, String warehousePrice) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.warehouseName = warehouseName;
        this.warehouseDescription = warehouseDescription;
        this.warehousePrice = warehousePrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseDescription() {
        return warehouseDescription;
    }

    public void setWarehouseDescription(String warehouseDescription) {
        this.warehouseDescription = warehouseDescription;
    }

    public String getWarehousePrice() {
        return warehousePrice;
    }

    public void setWarehousePrice(String warehousePrice) {
        this.warehousePrice = warehousePrice;
    }
}
