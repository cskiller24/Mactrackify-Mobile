package com.example.mactrackifyalpha;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import com.example.mactrackifyalpha.Entities.AuthEntity;
import com.example.mactrackifyalpha.Entities.CheckAuthEntity;
import com.example.mactrackifyalpha.Entities.LoginEntity;
import com.example.mactrackifyalpha.Entities.ValidationErrorResponse;
import com.example.mactrackifyalpha.Helpers.DefaultError;
import com.example.mactrackifyalpha.Helpers.ResponseConstants;
import com.example.mactrackifyalpha.Helpers.Storage;
import com.example.mactrackifyalpha.RequestHelpers.AuthRequestHelper;
import com.example.mactrackifyalpha.RequestHelpers.ResponseCallback;

public class Login extends AppCompatActivity {
    EditText email;
    EditText password;
    AlertDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView( R.layout.activity_login );

        Button loginbutton = findViewById( R.id.Login);

        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logging in");
        builder.setMessage("Loading...");
        loading = builder.create();
        loading.setCanceledOnTouchOutside(false);
        loading.setCancelable(false);

        loginbutton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading.show();
                LoginEntity loginEntity = new LoginEntity(email.getText().toString(), password.getText().toString());
                loginAccount(loginEntity);
            }
        });

        checkAuth();

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                finish();
            }
        });
    }

    private void checkAuth() {
        AuthRequestHelper auth = new AuthRequestHelper();
        Storage storage = new Storage(getApplicationContext());
        String key = storage.getStringPreference(Storage.AUTH_KEY, null);

        if (key != null) {
            auth.checkLogin(key, new ResponseCallback<CheckAuthEntity>() {
                @Override
                public void onSuccess(String message, int statusCode, CheckAuthEntity data) {
                    Toast.makeText(Login.this, "Already logged in redirect to main page", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, MainActivity.class));
                    finish();
                }

                @Override
                public void onFailure(String error, int statusCode) {
                    storage.remove(key);
                }

                @Override
                public void onValidationError(ValidationErrorResponse validationError) {
                }
            });
        }
    }

    private void loginAccount(LoginEntity entity) {
        AuthRequestHelper auth = new AuthRequestHelper();

        auth.login(entity, new ResponseCallback<AuthEntity>() {
            @Override
            public void onSuccess(String message, int statusCode, AuthEntity data) {
                loading.show();
                Storage storage = new Storage(getApplicationContext());

                storage.setPreference(Storage.AUTH_KEY, data.getData().getToken());

                startActivity(new Intent(Login.this, MainActivity.class));
            }

            @Override
            public void onFailure(String error, int statusCode) {
                loading.hide();
                if(statusCode == ResponseConstants.FORBIDDEN) {
                    Toast.makeText(Login.this, "You must be a brand ambassador to login", Toast.LENGTH_SHORT).show();
                    return;
                }
                DefaultError defaultError = new DefaultError(error, statusCode, getApplicationContext());
            }

            @Override
            public void onValidationError(ValidationErrorResponse validationError) {
                loading.hide();
                if(validationError.getError("email") != null) {
                    email.setError(validationError.getError("email"));
                }

                if(validationError.getError("password") != null) {
                    password.setError(validationError.getError("password"));
                }

                Toast.makeText(Login.this, "" + validationError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}