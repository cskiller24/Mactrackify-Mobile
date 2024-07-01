package com.example.mactrackifyalpha.Entities;

public class TrackerEntity {
    private float latitude;
    private float longitude;
    private boolean is_authentic;

    private String location;

    public TrackerEntity(float latitude, float longitude, boolean is_authentic, String location) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.is_authentic = is_authentic;
        this.location = location;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public boolean isIs_authentic() {
        return is_authentic;
    }

    public void setIs_authentic(boolean is_authentic) {
        this.is_authentic = is_authentic;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
