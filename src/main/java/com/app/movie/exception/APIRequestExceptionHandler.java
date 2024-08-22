package com.app.movie.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@ControllerAdvice
public class APIRequestExceptionHandler {

    // handling custom not found exception
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

    // handling custom bad request exception
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException badRequest) {
        ErrorResponse response = new ErrorResponse(
                badRequest.getMessage(),
                HttpStatus.BAD_REQUEST,
                400,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // handling invalid field
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<InvalidResponse> handleBadRequest(MethodArgumentNotValidException badRequest) {
        List<Object> messages = new ArrayList<>();
        badRequest.getBindingResult().getAllErrors().forEach(err -> {
            String field = ((FieldError)err).getField();
            String message = err.getDefaultMessage();
            messages.add(new HashMap<>(){{ put(field, message); }});
        });
        InvalidResponse response = new InvalidResponse(
                HttpStatus.BAD_REQUEST,
                400,
                messages,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
