package com.example.mactrackifyalpha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mactrackifyalpha.Entities.MessageEntity;
import com.example.mactrackifyalpha.Entities.ScheduleEntity;
import com.example.mactrackifyalpha.Entities.SchedulingEntity;
import com.example.mactrackifyalpha.Entities.SchedulingUpdateEntity;
import com.example.mactrackifyalpha.Entities.ValidationErrorResponse;
import com.example.mactrackifyalpha.Helpers.Storage;
import com.example.mactrackifyalpha.Recylers.SchedulingAdapter;
import com.example.mactrackifyalpha.RequestHelpers.ResponseCallback;
import com.example.mactrackifyalpha.RequestHelpers.SchedulingRequestHelper;

import java.util.ArrayList;

public class Schedule extends AppCompatActivity {

    RecyclerView dataRecycler;
    ArrayList<ScheduleEntity> scheduleEntities;
    SchedulingAdapter schedulingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_schedule );

        SchedulingRequestHelper request = new SchedulingRequestHelper();
        Storage storage = new Storage(getApplicationContext());

        dataRecycler = findViewById(R.id.dataRecycler);

        scheduleEntities = new ArrayList<>();
        schedulingAdapter = new SchedulingAdapter(getApplicationContext(), scheduleEntities);
        dataRecycler.setAdapter(schedulingAdapter);
        dataRecycler.setLayoutManager(new LinearLayoutManager(this));

        request.getSchedule(storage.getStringPreference(Storage.AUTH_KEY, ""), new ResponseCallback<ArrayList<ScheduleEntity>>() {
            @Override
            public void onSuccess(String message, int statusCode, ArrayList<ScheduleEntity> data) {
                scheduleEntities.addAll(data);
                schedulingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String error, int statusCode) {

            }

            @Override
            public void onValidationError(ValidationErrorResponse validationError) {

            }
        });
    }
}