package com.app.movie.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class APIRequestExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorResponse> HandleNotFound(NotFoundException notFound) {
        ErrorResponse response = new ErrorResponse(
                notFound.getMessage(),
                HttpStatus.NOT_FOUND,
                404,
                ZonedDateTime.now()
        );
        return new ResponseEntity(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException badRequest) {
        ErrorResponse response = new ErrorResponse(
                badRequest.getMessage(),
                HttpStatus.BAD_REQUEST,
                405,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
