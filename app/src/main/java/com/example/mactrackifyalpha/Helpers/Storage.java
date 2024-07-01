package com.example.mactrackifyalpha.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

public class Storage {

    public static final String MAIN_KEY = "mactrackify";
    public static final String AUTH_KEY = "auth_key";

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;
    public Storage (Context context, String key) {
        sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
    }

    public Storage (Context context) {
        sharedPreferences = context.getSharedPreferences(Storage.MAIN_KEY, Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

    }

    public void setPreference(String key, String value) {
        sharedPreferencesEditor.putString(key, value);
        sharedPreferencesEditor.apply();
    }

    public void setPreference(String key, int value) {

        sharedPreferencesEditor.putInt(key, value);
        sharedPreferencesEditor.apply();

    }

    public void setPreference(String key, float value) {
        sharedPreferencesEditor.putFloat(key, value);
        sharedPreferencesEditor.apply();

    }

    public void setPreference(String key, boolean value) {
        sharedPreferencesEditor.putBoolean(key, value);
        sharedPreferencesEditor.apply();

    }

    public String getStringPreference(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public int getIntPreference(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public float getFloatPreference(String key, float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    public boolean getBooleanPreference(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public void remove(String key) {
        sharedPreferencesEditor.remove(key);
        sharedPreferencesEditor.apply();
    }
}
