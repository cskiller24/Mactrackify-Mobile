package com.example.mactrackifyalpha.Recylers;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mactrackifyalpha.Entities.MessageEntity;
import com.example.mactrackifyalpha.Entities.ScheduleEntity;
import com.example.mactrackifyalpha.Entities.ValidationErrorResponse;
import com.example.mactrackifyalpha.Helpers.Storage;
import com.example.mactrackifyalpha.MainActivity;
import com.example.mactrackifyalpha.R;
import com.example.mactrackifyalpha.RequestHelpers.ResponseCallback;
import com.example.mactrackifyalpha.RequestHelpers.SchedulingRequestHelper;

import java.util.ArrayList;

public class SchedulingAdapter extends RecyclerView.Adapter<SchedulingAdapter.ScheduleHolder> {
    Context context;
    ArrayList<ScheduleEntity> scheduleEntities;
    SchedulingRequestHelper requestHelper;
    Storage storage;

    public SchedulingAdapter(Context context, ArrayList<ScheduleEntity> scheduleEntities) {
        this.context = context;
        this.scheduleEntities = scheduleEntities;
        requestHelper = new SchedulingRequestHelper();
        storage = new Storage(context);
    }

    @NonNull
    @Override
    public ScheduleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_item, parent, false);
        return new ScheduleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleHolder holder, int position) {
        ScheduleEntity entity = scheduleEntities.get(position);

        holder.date.setText(entity.getDate());
        holder.status.setText(entity.getStatusText());

        if(entity.isAccepted()) {
            holder.accept.setHeight(0);
            holder.decline.setHeight(0);
            holder.accept.setVisibility(View.GONE);
            holder.decline.setVisibility(View.GONE);
            holder.card.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.success)));
            holder.date.setTextColor(context.getColor(R.color.white));
            holder.status.setTextColor(context.getColor(R.color.white));
        } else if(entity.isDeclined()) {
            holder.accept.setHeight(0);
            holder.decline.setHeight(0);
            holder.accept.setVisibility(View.GONE);
            holder.decline.setVisibility(View.GONE);
            holder.date.setTextColor(context.getColor(R.color.white));
            holder.status.setTextColor(context.getColor(R.color.white));
            holder.card.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.danger)));
        }

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestHelper.acceptSchedule(storage.getStringPreference(Storage.AUTH_KEY, ""), scheduleEntities.get(holder.getAdapterPosition()).getId(), new ResponseCallback<MessageEntity>() {
                    @Override
                    public void onSuccess(String message, int statusCode, MessageEntity data) {
                        Toast.makeText(context, "Schedule accepted successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onFailure(String error, int statusCode) {
                        Toast.makeText(context, "Error on accepting schedule", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }

                    @Override
                    public void onValidationError(ValidationErrorResponse validationError) {

                    }
                });
            }
        });

        holder.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestHelper.declineSchedule(storage.getStringPreference(Storage.AUTH_KEY, ""), scheduleEntities.get(holder.getAdapterPosition()).getId(), new ResponseCallback<MessageEntity>() {
                    @Override
                    public void onSuccess(String message, int statusCode, MessageEntity data) {
                        Toast.makeText(context, "Schedule declined successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onFailure(String error, int statusCode) {
                        Toast.makeText(context, "Error on declining schedule", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onValidationError(ValidationErrorResponse validationError) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return scheduleEntities.size();
    }

    public class ScheduleHolder extends RecyclerView.ViewHolder {

        TextView date, status;
        Button accept, decline;
        CardView card;

        public ScheduleHolder(@NonNull View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.card);
            date = itemView.findViewById(R.id.date);
            status = itemView.findViewById(R.id.status);
            accept = itemView.findViewById(R.id.accept);
            decline = itemView.findViewById(R.id.decline);
        }
    }

}
