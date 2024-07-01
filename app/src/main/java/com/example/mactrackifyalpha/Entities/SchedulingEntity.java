package com.example.mactrackifyalpha.Entities;

import com.google.gson.annotations.SerializedName;

public class SchedulingEntity {

    @SerializedName("message")
    private String message;

    @SerializedName("has_deployment")
    private boolean hasDeployment;

    @SerializedName("data")
    private Deployment data;

    public SchedulingEntity(String message, boolean hasDeployment, Deployment data) {
        this.message = message;
        this.hasDeployment = hasDeployment;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isHasDeployment() {
        return hasDeployment;
    }

    public void setHasDeployment(boolean hasDeployment) {
        this.hasDeployment = hasDeployment;
    }

    public Deployment getData() {
        return data;
    }

    public void setData(Deployment data) {
        this.data = data;
    }

    public class Deployment {

        @SerializedName("status")
        private String status;

        @SerializedName("date")
        private String date;

        @SerializedName("id")
        private String id;

        public Deployment(String status, String date, String id) {
            this.status = status;
            this.date = date;
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
