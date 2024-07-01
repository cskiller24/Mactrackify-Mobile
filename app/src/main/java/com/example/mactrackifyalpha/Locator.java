package com.example.mactrackifyalpha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mactrackifyalpha.Services.TrackingService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class Locator extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, OnMapReadyCallback {

    private String TAG = "LOCATOR";
    final int COURSE_LOCATION_PERMISSION = 111;
    final int FINE_LOCATION_PERMISSION = 112;
    Button tracking;
    GoogleMap map;
    AlertDialog loading;
    public boolean isTrackingEnabled;
    FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_locator );
        accessPermissions();
        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Processing");
        builder.setMessage("Loading...");
        loading = builder.create();
        loading.setCanceledOnTouchOutside(false);
        loading.setCancelable(false);
        loading.show();
        fragment.getMapAsync(this);

        tracking = findViewById(R.id.btnTracking);
        if(isServiceRunning(TrackingService.class)) {
            tracking.setText("Click to disable tracking");
            isTrackingEnabled = true;
        } else {
            tracking.setText("Click to enable tracking");
            isTrackingEnabled = false;
        }

        tracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTrackingEnabled) {
                    tracking.setText("Click to enable tracking");
                    isTrackingEnabled = false;
                    disableTracking();
                } else {
                    if(! isLocationEnabled()) {
                        Toast.makeText(Locator.this, "Cannot activate the tracking, please enable location first", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    tracking.setText("Click to disable tracking");
                    isTrackingEnabled = true;
                    enableTracking();
                }
            }
        });
    }

    private void disableTracking() {
        Log.d(TAG, "disableTracking: DISABLED TRACKING");
        this.getApplicationContext().stopService(new Intent(Locator.this, TrackingService.class));
    }

    private void enableTracking() {
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null) {
                    map.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())));
                }
            }
        });
        this.getApplicationContext().startService(new Intent(Locator.this, TrackingService.class));
    }

    private void accessPermissions() {
        if(! EasyPermissions.hasPermissions(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
            EasyPermissions.requestPermissions(
                    this,
                    "DRRM mobile application requires location in order for the application to work",
                    COURSE_LOCATION_PERMISSION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
            );
        } else if (! EasyPermissions.hasPermissions(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            EasyPermissions.requestPermissions(
                    this,
                    "DRRM mobile application requires location in order for the application to work",
                    FINE_LOCATION_PERMISSION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
            );
        }
    }

    public boolean isLocationEnabled() {
        LocationManager lm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
            return false;
        }

        return true;
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
            startActivity(new Intent(Locator.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        loading.hide();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if(serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}