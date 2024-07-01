package com.example.mactrackifyalpha.Interfaces;

import com.example.mactrackifyalpha.Entities.MessageEntity;
import com.example.mactrackifyalpha.Entities.ScheduleEntity;
import com.example.mactrackifyalpha.Entities.SchedulingEntity;
import com.example.mactrackifyalpha.Entities.SchedulingUpdateEntity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SchedulingInterface {

    @GET("/api/schedule")
    Call<ArrayList<ScheduleEntity>> getSchedule(@Header("Authorization") String token);

    @GET("/api/schedule/{id}/accept")
    Call<MessageEntity> acceptSchedule(@Header("Authorization") String token, @Path ("id") String id);

    @GET("/api/schedule/{id}/decline")
    Call<MessageEntity> declineSchedule(@Header("Authorization") String token, @Path ("id") String id);
}
