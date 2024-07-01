package com.example.mactrackifyalpha.Services;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.mactrackifyalpha.Entities.MessageEntity;
import com.example.mactrackifyalpha.Entities.TrackerEntity;
import com.example.mactrackifyalpha.Entities.ValidationErrorResponse;
import com.example.mactrackifyalpha.Helpers.NotificationHelper;
import com.example.mactrackifyalpha.Helpers.Storage;
import com.example.mactrackifyalpha.Locator;
import com.example.mactrackifyalpha.RequestHelpers.ResponseCallback;
import com.example.mactrackifyalpha.RequestHelpers.TrackingRequestHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class TrackingService extends Service {

    private boolean isRunning = false;
    private Handler handler = new Handler();
    private FusedLocationProviderClient fusedLocationProviderClient;
    TrackingRequestHelper trackingRequestHelper;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        isRunning = true;
        NotificationHelper.createNotificationChannel(this.getApplicationContext());

        trackingRequestHelper = new TrackingRequestHelper();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            startMyOwnForeground();
        }
        else {
            startForeground(1, new Notification());
        }
        startBackgroundTask();

        return START_STICKY;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void startMyOwnForeground() {
        String NOTIFICATION_CHANNEL_ID = "example.permanence";
        String channelName = "Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(1002, notification);
    }

    private void startBackgroundTask() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(! isLocationEnabled()) {
                    NotificationHelper.createNotification(
                            "You have disabled your location services",
                            "The background tasks deleted and does not update the location",
                            getApplicationContext(),
                            Locator.class
                    );
                    stopService(new Intent(TrackingService.this, TrackingService.class));
                    Log.i("LOCATION HANDLER", "NO LOCATION");
                    return;
                }
                handler.postDelayed(this, 30000);
            }
        }, 0);

        LocationRequest request = new LocationRequest();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(60 * 1000);
        request.setFastestInterval(60 * 1000);

        LocationCallback callback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                Location location = locationResult.getLastLocation();

                if (location == null) {
                    Toast.makeText(TrackingService.this, "Please enable your location", Toast.LENGTH_SHORT).show();

                    return;
                }

                if(location.isFromMockProvider()) {
                    NotificationHelper.createNotification(
                            "YOU ARE USING FAKE LOCATION",
                            "Please remove your fake location",
                            getApplicationContext(),
                            Locator.class
                    );
                };

                try {

                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                    String fullAddress = "Null";
                    if(addresses != null && addresses.size() > 0) {
                        Address address = addresses.get(0);

                        fullAddress = address.getAddressLine(0) + " " + address.getLocality() + ", " + address.getCountryName();

                    }
                    TrackerEntity entity = new TrackerEntity((float) location.getLatitude(), (float) location.getLongitude(), !location.isFromMockProvider(), fullAddress);

                    Storage storage = new Storage(getApplicationContext());
                    trackingRequestHelper.createTrack(
                            storage.getStringPreference(Storage.AUTH_KEY, ""),
                            entity,
                            new ResponseCallback<MessageEntity>() {
                                @Override
                                public void onSuccess(String message, int statusCode, MessageEntity data) {
                                    Toast.makeText(TrackingService.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(String error, int statusCode) {
                                    Log.i("ERROR", error);
                                    Toast.makeText(TrackingService.this, "Something went wrong please try again" + error, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onValidationError(ValidationErrorResponse validationError) {
                                }
                            }
                    );
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        fusedLocationProviderClient.requestLocationUpdates(request, callback, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Removing tracking service", Toast.LENGTH_SHORT).show();
        isRunning = false;
        fusedLocationProviderClient.removeLocationUpdates(new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
            }
        });
        handler.removeCallbacksAndMessages(null);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
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
}
