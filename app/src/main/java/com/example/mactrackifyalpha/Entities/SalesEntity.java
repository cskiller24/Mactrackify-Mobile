package com.example.mactrackifyalpha.Entities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SalesEntity {
    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private ArrayList<Data> data;

    public SalesEntity(String message, ArrayList<Data> data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public class Data {

        public Data(String customerName, String customerContact, int age, String brandAmbassadorName, String productName, int productQuantity, String promo, int promoQuantity, String date, String createdAtDiff, String signatureUrl) {
            this.customerName = customerName;
            this.customerContact = customerContact;
            this.age = age;
            this.brandAmbassadorName = brandAmbassadorName;
            this.productName = productName;
            this.productQuantity = productQuantity;
            this.promo = promo;
            this.promoQuantity = promoQuantity;
            this.date = date;
            this.createdAtDiff = createdAtDiff;
            this.signatureUrl = signatureUrl;
        }
        @SerializedName("customer_name")
        private String customerName;

        @SerializedName("customer_contact")
        private String customerContact;

        @SerializedName("age")
        private int age;

        @SerializedName("brand_ambassador_name")
        private String brandAmbassadorName;
        @SerializedName("product_name")
        private String productName;

        @SerializedName("product_quantity")
        private int productQuantity;

        @SerializedName("promo")
        private String promo;

        @SerializedName("promo_quantity")
        private int promoQuantity;

        @SerializedName("date")
        private String date;

        @SerializedName("created_at_diff")
        private String createdAtDiff;

        @SerializedName("signature_url")
        private String signatureUrl;

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerContact() {
            return customerContact;
        }

        public void setCustomerContact(String customerContact) {
            this.customerContact = customerContact;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getBrandAmbassadorName() {
            return brandAmbassadorName;
        }

        public void setBrandAmbassadorName(String brandAmbassadorName) {
            this.brandAmbassadorName = brandAmbassadorName;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getProductQuantity() {
            return productQuantity;
        }

        public void setProductQuantity(int productQuantity) {
            this.productQuantity = productQuantity;
        }

        public String getPromo() {
            return promo;
        }

        public void setPromo(String promo) {
            this.promo = promo;
        }

        public int getPromoQuantity() {
            return promoQuantity;
        }

        public void setPromoQuantity(int promoQuantity) {
            this.promoQuantity = promoQuantity;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCreatedAtDiff() {
            return createdAtDiff;
        }

        public void setCreatedAtDiff(String createdAtDiff) {
            this.createdAtDiff = createdAtDiff;
        }

        public String getSignatureUrl() {
            return signatureUrl;
        }

        public void setSignatureUrl(String signatureUrl) {
            this.signatureUrl = signatureUrl;
        }
    }
}
