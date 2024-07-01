package com.example.mactrackifyalpha.Interfaces;

import com.example.mactrackifyalpha.Entities.MessageEntity;
import com.example.mactrackifyalpha.Entities.TrackerEntity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface TrackingInterface {

    @POST("api/tracking")
    Call<MessageEntity> createTrack(@Header("Authorization") String token, @Body TrackerEntity entity);
}
