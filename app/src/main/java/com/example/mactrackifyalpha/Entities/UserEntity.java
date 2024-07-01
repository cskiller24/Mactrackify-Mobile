package com.example.mactrackifyalpha.Entities;

import com.google.gson.annotations.SerializedName;

public class UserEntity {
    @SerializedName("id")
    private String id;
    @SerializedName("email")
    private String email;
    @SerializedName("fullName")
    private String fullName;
    public UserEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
