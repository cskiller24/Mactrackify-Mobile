package com.example.mactrackifyalpha.Interfaces;

import com.example.mactrackifyalpha.Entities.AccountsEntity;
import com.example.mactrackifyalpha.Entities.AddSalesPost;
import com.example.mactrackifyalpha.Entities.MessageEntity;
import com.example.mactrackifyalpha.Entities.TestBody;
import com.example.mactrackifyalpha.Entities.WarehouseItemEntity;
import com.example.mactrackifyalpha.RequestHelpers.ResponseCallback;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface CreateSalesInterface {

    @GET("/api/accounts")
    Call<AccountsEntity> getAccounts(@Header("Authorization") String token);

    @GET("/api/warehouse-items")
    Call<WarehouseItemEntity> getWarehouseItems(@Header("Authorization") String token);

    @PUT("/api/transactions")
    Call<MessageEntity> postTransaction(@Header("Authorization") String token, @Body AddSalesPost post);
}
