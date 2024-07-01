package com.example.mactrackifyalpha.RequestHelpers;

import com.example.mactrackifyalpha.Entities.MessageEntity;
import com.example.mactrackifyalpha.Entities.TrackerEntity;
import com.example.mactrackifyalpha.Entities.ValidationErrorResponse;
import com.example.mactrackifyalpha.Helpers.ResponseConstants;
import com.example.mactrackifyalpha.Interfaces.SalesInterface;
import com.example.mactrackifyalpha.Interfaces.TrackingInterface;
import com.example.mactrackifyalpha.Library.Factory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TrackingRequestHelper {

    TrackingInterface trackingInterface;

    Retrofit retrofit;

    public TrackingRequestHelper() {
        retrofit = Factory.getApiInstance();

        trackingInterface = retrofit.create(TrackingInterface.class);
    }

    public void createTrack(String token, TrackerEntity data, ResponseCallback<MessageEntity> callback) {
        Call<MessageEntity> call = trackingInterface.createTrack("Bearer " + token, data);

        call.enqueue(new Callback<MessageEntity>() {
            @Override
            public void onResponse(Call<MessageEntity> call, Response<MessageEntity> response) {
                if(response.isSuccessful()) {
                    callback.onSuccess(response.message(), response.code(), response.body());
                    return;
                }

                if(response.code() == ResponseConstants.UNPROCESSABLE_ENTITY) {
                    callback.onValidationError(new ValidationErrorResponse(response.errorBody()));
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
