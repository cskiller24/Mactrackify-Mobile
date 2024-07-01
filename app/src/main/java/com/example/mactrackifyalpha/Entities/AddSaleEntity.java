package com.example.mactrackifyalpha.Entities;

public class AddSaleEntity {
    private int id;
    private AddSaleProductEntity addSaleProduct;
    private int quantity;

    public AddSaleEntity() {
    }
    public AddSaleEntity(int id) {
        this.id = id;
    }

    public AddSaleEntity(int id, AddSaleProductEntity addSaleProduct, int quantity) {
        this.id = id;
        this.addSaleProduct = addSaleProduct;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AddSaleProductEntity getAddSaleProduct() {
        return addSaleProduct;
    }

    public void setAddSaleProduct(AddSaleProductEntity addSaleProduct) {
        this.addSaleProduct = addSaleProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
