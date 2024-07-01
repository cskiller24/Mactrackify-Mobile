package com.example.mactrackifyalpha.Helpers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class DefaultError {
    public DefaultError(String message, int code, Context context) {
        Toast.makeText(context, "Something went wrong please try again.", Toast.LENGTH_SHORT).show();
        Log.e(context.getClass().getSimpleName() + " : RESPONSE ERROR", message);
    }
}
