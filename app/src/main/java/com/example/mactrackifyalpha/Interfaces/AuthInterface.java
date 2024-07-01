package com.example.mactrackifyalpha.Interfaces;

import com.example.mactrackifyalpha.Entities.AuthEntity;
import com.example.mactrackifyalpha.Entities.CheckAuthEntity;
import com.example.mactrackifyalpha.Entities.LoginEntity;
import com.example.mactrackifyalpha.Entities.MessageEntity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthInterface {
    @GET("/api/auth")
    Call<CheckAuthEntity> check(@Header("Authorization") String token);

    @POST("/api/login")
    Call<AuthEntity> login(@Body LoginEntity entity);

    @POST("/api/logout")
    Call<MessageEntity> logout(@Header("Authorization") String token);
}
