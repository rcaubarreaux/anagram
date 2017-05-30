package com.caubarreaux.error;

import com.caubarreaux.exception.BadRequestException;
import com.caubarreaux.exception.BaseApiException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * User: ross
 * Date: 5/20/17
 * Time: 9:40 AM
 */

@ControllerAdvice
public class ControllerExceptionHandler {

    private ResponseEntity<ErrorMessageResponse> createErrorMessageResponse(String errorMessage, HttpStatus httpStatus) {
        ErrorMessageResponse error = new ErrorMessageResponse(errorMessage, httpStatus.value());
        return new ResponseEntity<ErrorMessageResponse>(error, httpStatus);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessageResponse> badRequestExceptionHandler(BaseApiException exception) {
        return createErrorMessageResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorMessageResponse> defaultExceptionHandler(Throwable exception) {
        return createErrorMessageResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
