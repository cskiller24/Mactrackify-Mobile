package com.example.mactrackifyalpha.RequestHelpers;

import android.os.StrictMode;
import android.util.Log;

import com.example.mactrackifyalpha.BuildConfig;
import com.example.mactrackifyalpha.Entities.AccountsEntity;
import com.example.mactrackifyalpha.Entities.AddSalesPost;
import com.example.mactrackifyalpha.Entities.MessageEntity;
import com.example.mactrackifyalpha.Entities.SalesEntity;
import com.example.mactrackifyalpha.Entities.TestBody;
import com.example.mactrackifyalpha.Entities.ValidationErrorResponse;
import com.example.mactrackifyalpha.Entities.WarehouseItemEntity;
import com.example.mactrackifyalpha.Helpers.ResponseConstants;
import com.example.mactrackifyalpha.Interfaces.CreateSalesInterface;
import com.example.mactrackifyalpha.Library.Factory;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SalesAddRequestHelper {
    CreateSalesInterface createSalesInterface;

    Retrofit retrofit;

    public SalesAddRequestHelper() {
        retrofit = Factory.getApiInstance();

        createSalesInterface = retrofit.create(CreateSalesInterface.class);
    }

    public void getAccounts(String token, ResponseCallback<AccountsEntity> callback) {
        Call<AccountsEntity> call = createSalesInterface.getAccounts("Bearer " + token);
        call.enqueue(new Callback<AccountsEntity>() {
            @Override
            public void onResponse(Call<AccountsEntity> call, Response<AccountsEntity> response) {
                if(response.isSuccessful()) {
                    callback.onSuccess(response.message(), response.code(), response.body());
                    return;
                }

                callback.onFailure(response.message(), response.code());
            }

            @Override
            public void onFailure(Call<AccountsEntity> call, Throwable t) {
                callback.onFailure(t.getMessage(), 500);
            }
        });
    }

    public void getWarehouseItems(String token, ResponseCallback<WarehouseItemEntity> callback) {
        Call<WarehouseItemEntity> call = createSalesInterface.getWarehouseItems("Bearer " + token);
        call.enqueue(new Callback<WarehouseItemEntity>() {
            @Override
            public void onResponse(Call<WarehouseItemEntity> call, Response<WarehouseItemEntity> response) {
                if(response.isSuccessful()) {
                    callback.onSuccess(response.message(), response.code(), response.body());
                    return;
                }

                callback.onFailure(response.message(), response.code());
            }

            @Override
            public void onFailure(Call<WarehouseItemEntity> call, Throwable t) {
                callback.onFailure(t.getMessage(), 500);
            }
        });
    }

    public void postTransaction(String token, AddSalesPost body, ResponseCallback<MessageEntity> callback) {
        Call<MessageEntity> call = createSalesInterface.postTransaction("Bearer " + token, body);
        call.enqueue(new Callback<MessageEntity>() {
            @Override
            public void onResponse(Call<MessageEntity> call, Response<MessageEntity> response) {
                if(response.code() == ResponseConstants.UNPROCESSABLE_ENTITY) {
                    callback.onValidationError(new ValidationErrorResponse(response.errorBody()));
                    return;
                }

                if(! response.isSuccessful()) {
                    callback.onFailure(response.message(), response.code());
                    return;
                }
                callback.onSuccess(response.message(), response.code(), response.body());
            }

            @Override
            public void onFailure(Call<MessageEntity> call, Throwable t) {
                callback.onFailure(t.getMessage(), 500);
                Log.i("RESPONSE ERROR", "" + t.getMessage());
            }
        });
    }
}
