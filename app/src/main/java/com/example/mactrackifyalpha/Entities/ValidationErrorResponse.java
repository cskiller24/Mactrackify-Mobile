package com.example.mactrackifyalpha.Entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

public class ValidationErrorResponse {
    private String message;
    private Map<String, List<String>> errors;

    public ValidationErrorResponse(ResponseBody body) {// COMPLETE this code it would parse the response body into clasess
        try {
            String jsonString = body.string();
            JSONObject jsonObject = new JSONObject(jsonString);
            this.message = jsonObject.getString("message");

            JSONObject errorsObject = jsonObject.getJSONObject("errors");
            this.errors = new HashMap<>();

            Iterator<String> keys = errorsObject.keys();
            while (keys.hasNext()) {
                String field = keys.next();
                JSONArray errorArray = errorsObject.getJSONArray(field);

                List<String> errorMessages = new ArrayList<>();
                for (int i = 0; i < errorArray.length(); i++) {
                    errorMessages.add(errorArray.getString(i));
                }

                this.errors.put(field, errorMessages);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public String getMessage() {
        return message;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }

    public String getError(String key) {
        List<String> errorMessages = errors.get(key);
        if (errorMessages != null && !errorMessages.isEmpty()) {
            return errorMessages.get(0); // Return the first error message for the given key
        } else {
            return null; // Return null if there are no errors for the given key
        }
    }
}
