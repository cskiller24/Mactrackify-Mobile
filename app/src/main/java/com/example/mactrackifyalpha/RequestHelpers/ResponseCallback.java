package com.example.mactrackifyalpha.RequestHelpers;

import com.example.mactrackifyalpha.Entities.ValidationErrorResponse;

public interface ResponseCallback<T> {
    void onSuccess(String message, int statusCode, T data);
    void onFailure(String error, int statusCode);
    void onValidationError(ValidationErrorResponse validationError);
}
