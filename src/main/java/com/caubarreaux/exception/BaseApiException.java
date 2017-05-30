package com.caubarreaux.exception;

/**
 * User: ross
 * Date: 5/20/17
 * Time: 9:34 AM
 */
public abstract class BaseApiException extends Exception {
    public BaseApiException() {
    }

    public BaseApiException(String message) {
        super(message);
    }

    public BaseApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseApiException(Throwable cause) {
        super(cause);
    }

    public BaseApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
