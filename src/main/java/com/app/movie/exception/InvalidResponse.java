package com.app.movie.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.List;

public class InvalidResponse {

    private final HttpStatus status;
    private final Integer statusCode;
    private final List<Object> messages;
    private final ZonedDateTime timestamp;

    public InvalidResponse(HttpStatus status, Integer statusCode, List<Object> messages, ZonedDateTime timestamp) {
        this.status = status;
        this.statusCode = statusCode;
        this.messages = messages;
        this.timestamp = timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public List<Object> getMessages() {
        return messages;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}
