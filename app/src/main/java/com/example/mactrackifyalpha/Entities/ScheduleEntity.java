package com.example.mactrackifyalpha.Entities;

import com.google.gson.annotations.SerializedName;

public class ScheduleEntity {
    @SerializedName("id")
    private String id;

    @SerializedName("date")
    private String date;

    @SerializedName("status")
    private String status;

    @SerializedName("status_text")
    private String statusText;

    public ScheduleEntity() {
    }

    public ScheduleEntity(String id, String date, String status, String statusText) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.statusText = statusText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public boolean isAccepted() {
        return this.status.equals("accepted");
    }

    public boolean isDeclined() {
        return this.status.equals("declined");
    }

    public boolean isNoResponse() {
        return this.status.equals("no_response");
    }

}
