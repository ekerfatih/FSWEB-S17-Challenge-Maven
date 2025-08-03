package com.workintech.spring17challenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleException(ApiException exception) {
        ApiErrorResponse apiResponseError = new ApiErrorResponse(
                exception.getHttpStatus().value(), exception.getMessage(), System.currentTimeMillis()
        );

        return new ResponseEntity<>(apiResponseError, exception.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleException(Exception exception) {
        ApiErrorResponse apiResponseError = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(), exception.getMessage(), System.currentTimeMillis()
        );

        return new ResponseEntity<>(apiResponseError, HttpStatus.BAD_REQUEST);
    }
}
