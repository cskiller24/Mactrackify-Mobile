package com.example.mactrackifyalpha.RequestHelpers;

import com.example.mactrackifyalpha.Entities.TransactionEntity;
import com.example.mactrackifyalpha.Interfaces.TransactionsInterface;
import com.example.mactrackifyalpha.Library.Factory;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TransactionsRequestHelper {
    Retrofit retrofit;

    TransactionsInterface transactionsInterface;
    public TransactionsRequestHelper() {
        retrofit = Factory.getApiInstance();

        transactionsInterface = retrofit.create(TransactionsInterface.class);
    }

    public void getTransactions(String token, ResponseCallback<ArrayList<TransactionEntity>> callback) {
        Call<ArrayList<TransactionEntity>> call = transactionsInterface.getTransactions("Bearer " + token);

        call.enqueue(new Callback<ArrayList<TransactionEntity>>() {
            @Override
            public void onResponse(Call<ArrayList<TransactionEntity>> call, Response<ArrayList<TransactionEntity>> response) {
                if(! response.isSuccessful()) {
                    callback.onFailure(response.message(), response.code());
                }

                callback.onSuccess(response.message(), response.code(), response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<TransactionEntity>> call, Throwable t) {
                callback.onFailure(t.getMessage(), 500);
            }
        });
    }

    public void getTransaction(String token, String uuid, ResponseCallback<TransactionEntity> callback) {
        Call<TransactionEntity> call = transactionsInterface.getTransaction("Bearer " + token, uuid);

        call.enqueue(new Callback<TransactionEntity>() {
            @Override
            public void onResponse(Call<TransactionEntity> call, Response<TransactionEntity> response) {
                if(! response.isSuccessful()) {
                    callback.onFailure(response.message(), response.code());
                }

                callback.onSuccess(response.message(), response.code(), response.body());
            }

            @Override
            public void onFailure(Call<TransactionEntity> call, Throwable t) {
                callback.onFailure(t.getMessage(), 500);
            }
        });
    }
}
