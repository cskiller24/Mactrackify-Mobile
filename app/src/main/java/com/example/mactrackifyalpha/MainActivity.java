package com.example.mactrackifyalpha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mactrackifyalpha.Entities.CheckAuthEntity;
import com.example.mactrackifyalpha.Entities.MessageEntity;
import com.example.mactrackifyalpha.Entities.ValidationErrorResponse;
import com.example.mactrackifyalpha.Helpers.Storage;
import com.example.mactrackifyalpha.RequestHelpers.AuthRequestHelper;
import com.example.mactrackifyalpha.RequestHelpers.ResponseCallback;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    Button Data;
    Button Schedule;
    Button Locator;
    Button Logout;

    final int COURSE_LOCATION_PERMISSION = 111;
    final int FINE_LOCATION_PERMISSION = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkLogin();
        accessPermissions();

        Data = findViewById(R.id.Data);
        Data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.example.mactrackifyalpha.Data.class);
                startActivity(intent);
            }
        });

        Schedule = findViewById(R.id.Schedule);
        Schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Schedule.class);
                startActivity(intent);
            }
        });

        Locator = findViewById(R.id.Locator);
        Locator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Locator.class);
                startActivity(intent);
            }
        });

        Logout = findViewById(R.id.Logout);
        Logout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        } );

    }

    private void logout() {
        Storage storage = new Storage(getApplicationContext(), Storage.MAIN_KEY);
        String key = storage.getStringPreference(Storage.AUTH_KEY, null);
        AuthRequestHelper helper = new AuthRequestHelper();
        helper.logout(key, new ResponseCallback<MessageEntity>() {
            @Override
            public void onSuccess(String message, int statusCode, MessageEntity data) {
                Storage storage = new Storage(getApplicationContext());
                storage.remove(Storage.AUTH_KEY);
                Toast.makeText(MainActivity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();

                startActivity(new Intent(MainActivity.this, Login.class));
                finish();
            }

            @Override
            public void onFailure(String error, int statusCode) {

            }

            @Override
            public void onValidationError(ValidationErrorResponse validationError) {

            }
        });
    }

    public void checkLogin() {
        Storage storage = new Storage(getApplicationContext(), Storage.MAIN_KEY);
        String key = storage.getStringPreference(Storage.AUTH_KEY, null);

        if(key == null) {
            Toast.makeText(this, "You are not logged. Redirecting to login page", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
        }

        AuthRequestHelper helper = new AuthRequestHelper();
        helper.checkLogin(key, new ResponseCallback<CheckAuthEntity>() {
            @Override
            public void onSuccess(String message, int statusCode, CheckAuthEntity data) {

            }

            @Override
            public void onFailure(String error, int statusCode) {
                Toast.makeText(MainActivity.this, "Something went wrong please try again.", Toast.LENGTH_SHORT).show();
                Log.i("ERROR: ", error);
            }

            @Override
            public void onValidationError(ValidationErrorResponse validationError) {
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void accessPermissions() {
        if(! EasyPermissions.hasPermissions(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
            EasyPermissions.requestPermissions(
                    this,
                    "Trackcertify mobile application requires location in order for the application to work",
                    COURSE_LOCATION_PERMISSION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
            );
        } else if (! EasyPermissions.hasPermissions(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            EasyPermissions.requestPermissions(
                    this,
                    "Trackcertify mobile application requires location in order for the application to work",
                    FINE_LOCATION_PERMISSION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
            );
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if(EasyPermissions.hasPermissions(this, String.valueOf(perms))) {
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(5000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationCallback locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
                    super.onLocationResult(locationResult);

                    Toast.makeText(MainActivity.this, "Retrieved last location", Toast.LENGTH_SHORT).show();
                }
            };

            LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}