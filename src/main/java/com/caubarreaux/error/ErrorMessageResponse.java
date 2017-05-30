package com.caubarreaux.error;

import lombok.Data;

/**
 * User: ross
 * Date: 5/25/17
 * Time: 9:48 AM
 */

@Data
public class ErrorMessageResponse {

    private String message;
    private int status;

    public ErrorMessageResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
