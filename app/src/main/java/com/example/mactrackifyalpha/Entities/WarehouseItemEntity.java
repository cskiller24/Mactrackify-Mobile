package com.example.mactrackifyalpha.Entities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WarehouseItemEntity {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private ArrayList<WarehouseItem> data;

    public WarehouseItemEntity() {
    }

    public WarehouseItemEntity(String message, ArrayList<WarehouseItem> data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<WarehouseItem> getData() {
        return data;
    }

    public void setData(ArrayList<WarehouseItem> data) {
        this.data = data;
    }

    public class WarehouseItem {
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;

        public WarehouseItem() {
        }

        public WarehouseItem(String id, String name) {
            this.id = id;
            this.name = name;
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
    }
}
