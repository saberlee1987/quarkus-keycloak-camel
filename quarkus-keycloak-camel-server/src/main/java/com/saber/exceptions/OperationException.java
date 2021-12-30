package com.saber.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OperationException extends RuntimeException {
    private String responseBody;
    private int statusCode;

    public OperationException(String responseBody, int statusCode) {
        this.responseBody = responseBody;
        this.statusCode = statusCode;
    }

    public OperationException(String message, String responseBody, int statusCode) {
        super(message);
        this.responseBody = responseBody;
        this.statusCode = statusCode;
    }

    public OperationException(String message, Throwable cause, String responseBody, int statusCode) {
        super(message, cause);
        this.responseBody = responseBody;
        this.statusCode = statusCode;
    }

    public OperationException(Throwable cause, String responseBody, int statusCode) {
        super(cause);
        this.responseBody = responseBody;
        this.statusCode = statusCode;
    }

    public OperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String responseBody, int statusCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.responseBody = responseBody;
        this.statusCode = statusCode;
    }
}
