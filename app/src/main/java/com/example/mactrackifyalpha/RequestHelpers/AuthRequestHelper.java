package com.example.mactrackifyalpha.RequestHelpers;

import android.util.Log;

import com.example.mactrackifyalpha.BuildConfig;
import com.example.mactrackifyalpha.Entities.AuthEntity;
import com.example.mactrackifyalpha.Entities.CheckAuthEntity;
import com.example.mactrackifyalpha.Entities.LoginEntity;
import com.example.mactrackifyalpha.Entities.MessageEntity;
import com.example.mactrackifyalpha.Entities.ValidationErrorResponse;
import com.example.mactrackifyalpha.Helpers.ResponseConstants;
import com.example.mactrackifyalpha.Interfaces.AuthInterface;
import com.example.mactrackifyalpha.Library.Factory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthRequestHelper {
    private String BACKEND_URL = BuildConfig.BACKEND_URL;
    private AuthInterface authInterface;

    public AuthRequestHelper() {
        Retrofit retrofit = Factory.getApiInstance();

        authInterface = retrofit.create(AuthInterface.class);
    }

    public void checkLogin(String loginToken, ResponseCallback<CheckAuthEntity> callback) {
        Call<CheckAuthEntity> call = authInterface.check("Bearer " + loginToken);
        call.enqueue(new Callback<CheckAuthEntity>() {
            @Override
            public void onResponse(Call<CheckAuthEntity> call, Response<CheckAuthEntity> response) {
                if(response.isSuccessful()) {
                    callback.onSuccess(response.message(), response.code(), response.body());
                } else {
                    callback.onFailure(response.message(), response.code());
                }
            }

            @Override
            public void onFailure(Call<CheckAuthEntity> call, Throwable t) {
                callback.onFailure(t.getMessage(), 500);
            }
        });
    }

    public void login(LoginEntity entity, ResponseCallback<AuthEntity> callback) {
        Call<AuthEntity> call = authInterface.login(entity);

        call.enqueue(new Callback<AuthEntity>() {
            @Override
            public void onResponse(Call<AuthEntity> call, Response<AuthEntity> response) {
                Log.i("LOG, CODE", "" + response.code());
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
            public void onFailure(Call<AuthEntity> call, Throwable t) {
                callback.onFailure(t.getMessage(), 500);
                Log.i("RESPONSE ERROR", "" + t.getMessage());
            }
        });
    }

    public void logout(String token, ResponseCallback<MessageEntity> callback) {
        Call<MessageEntity> call = authInterface.logout("Bearer " + token);
        call.enqueue(new Callback<MessageEntity>() {
            @Override
            public void onResponse(Call<MessageEntity> call, Response<MessageEntity> response) {
                callback.onSuccess(response.message(), response.code(), response.body());
            }

            @Override
            public void onFailure(Call<MessageEntity> call, Throwable t) {
                Log.e("LOGOUT ERROR", "" + t.getMessage());

                callback.onFailure(t.getMessage(), 500);
            }
        });
    }
}
