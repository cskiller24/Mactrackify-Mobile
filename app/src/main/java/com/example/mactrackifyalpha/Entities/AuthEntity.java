package com.example.mactrackifyalpha.Entities;

import com.google.gson.annotations.SerializedName;

public class AuthEntity {
    @SerializedName("data")
    private Data data;
    @SerializedName("message")
    private String message;

    public AuthEntity(Data data, String message) {
        this.data = data;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private String token;


        public Data(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}


