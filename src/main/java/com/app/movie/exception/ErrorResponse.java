package com.app.movie.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ErrorResponse {
    private final String message;
    private final HttpStatus httpStatus;
    private final Integer httpStatusCode;
    private final ZonedDateTime timestamp;

    public ErrorResponse(String message,
                            HttpStatus httpStatus,
                            Integer httpStatusCode,
                            ZonedDateTime timestamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.httpStatusCode = httpStatusCode;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}
