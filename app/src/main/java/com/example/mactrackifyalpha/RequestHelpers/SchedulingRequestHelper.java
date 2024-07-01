package com.example.mactrackifyalpha.RequestHelpers;

import com.example.mactrackifyalpha.Entities.MessageEntity;
import com.example.mactrackifyalpha.Entities.ScheduleEntity;
import com.example.mactrackifyalpha.Entities.SchedulingEntity;
import com.example.mactrackifyalpha.Entities.SchedulingUpdateEntity;
import com.example.mactrackifyalpha.Interfaces.SchedulingInterface;
import com.example.mactrackifyalpha.Library.Factory;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SchedulingRequestHelper {

    SchedulingInterface schedulingInterface;

    Retrofit retrofit;

    public SchedulingRequestHelper() {
        retrofit = Factory.getApiInstance();

        schedulingInterface = retrofit.create(SchedulingInterface.class);
    }

    public void getSchedule(String loginToken, ResponseCallback<ArrayList<ScheduleEntity>> callback) {
        Call<ArrayList<ScheduleEntity>> call = schedulingInterface.getSchedule("Bearer " + loginToken);

        call.enqueue(new Callback<ArrayList<ScheduleEntity>>() {
            @Override
            public void onResponse(Call<ArrayList<ScheduleEntity>> call, Response<ArrayList<ScheduleEntity>> response) {
                if(response.isSuccessful()) {
                    callback.onSuccess(response.message(), response.code(), response.body());
                    return;
                }

                callback.onFailure(response.message(), response.code());
            }

            @Override
            public void onFailure(Call<ArrayList<ScheduleEntity>> call, Throwable t) {
                callback.onFailure(t.getMessage(), 500);
            }
        });
    }

    public void acceptSchedule(String token, String id, ResponseCallback<MessageEntity> callback) {
        Call<MessageEntity> call = schedulingInterface.acceptSchedule("Bearer " + token, id);
        call.enqueue(new Callback<MessageEntity>() {
            @Override
            public void onResponse(Call<MessageEntity> call, Response<MessageEntity> response) {
                if(response.isSuccessful()) {
                    callback.onSuccess(response.message(), response.code(), response.body());
                    return;
                }

                callback.onFailure(response.message(), response.code());
            }

            @Override
            public void onFailure(Call<MessageEntity> call, Throwable t) {
                callback.onFailure(t.getMessage(), 500);
            }
        });
    }

    public void declineSchedule(String token, String id, ResponseCallback<MessageEntity> callback) {
        Call<MessageEntity> call = schedulingInterface.declineSchedule("Bearer " + token, id);
        call.enqueue(new Callback<MessageEntity>() {
            @Override
            public void onResponse(Call<MessageEntity> call, Response<MessageEntity> response) {
                if(response.isSuccessful()) {
                    callback.onSuccess(response.message(), response.code(), response.body());
                    return;
                }

                callback.onFailure(response.message(), response.code());
            }

            @Override
            public void onFailure(Call<MessageEntity> call, Throwable t) {
                callback.onFailure(t.getMessage(), 500);
            }
        });
    }
}
