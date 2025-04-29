package com.blog.reactive.infrastructure;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

// the annotation below marks this class as a global exception handler
@RestControllerAdvice
public class GlobalExceptionHandler {

    // handles duplicate key exception
    @ExceptionHandler(DuplicateKeyException.class)
    public Mono<ErrorResponse> handleDuplicateKeyException(DuplicateKeyException exception) {
        return Mono.just(ErrorResponse.builder(exception, HttpStatus.CONFLICT, exception.getMessage()).build());
    }

    //handles general exception
    @ExceptionHandler(Exception.class)
    public Mono<ErrorResponse> handleGeneralException(Exception exception) {
        return Mono.just(ErrorResponse.builder(exception, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()).build());
    }

    //handles bad requests
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ErrorResponse> handleWebExchangeBindException(WebExchangeBindException exception) {
        String errorMessage = exception.getBindingResult().getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining(", "));
        return Mono.just(ErrorResponse.builder(exception, HttpStatus.BAD_REQUEST, errorMessage).build());
    }


}
