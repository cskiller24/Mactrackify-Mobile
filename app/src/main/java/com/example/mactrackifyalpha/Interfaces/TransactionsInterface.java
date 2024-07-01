package com.example.mactrackifyalpha.Interfaces;

import com.example.mactrackifyalpha.Entities.TransactionEntity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface TransactionsInterface {
    @GET("/api/transactions")
    Call<ArrayList<TransactionEntity>> getTransactions(@Header("Authorization") String token);

    @GET("/api/transactions/{uuid}")
    Call<TransactionEntity> getTransaction(@Header("Authorization") String token, @Path("uuid") String uuid);
}
