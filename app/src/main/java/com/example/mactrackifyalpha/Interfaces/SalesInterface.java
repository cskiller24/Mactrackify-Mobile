package com.example.mactrackifyalpha.Interfaces;

import com.example.mactrackifyalpha.Entities.SalesEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface SalesInterface {
    @GET("/api/sales")
    Call<SalesEntity> getSales(@Header("Authorization") String token);
}
