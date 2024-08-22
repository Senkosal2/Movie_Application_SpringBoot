package com.app.movie.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ErrorResponse {
    private final String message;
    private final HttpStatus status;
    private final Integer statusCode;
    private final ZonedDateTime timestamp;

    public ErrorResponse(String message, HttpStatus status, Integer statusCode, ZonedDateTime timestamp) {
        this.message = message;
        this.status = status;
        this.statusCode = statusCode;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}
