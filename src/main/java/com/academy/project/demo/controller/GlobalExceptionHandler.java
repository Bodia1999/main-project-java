package com.academy.project.demo.controller;

import com.academy.project.demo.dto.response.ErrorResponse;
import com.academy.project.demo.exception.BadRequestException;
import com.academy.project.demo.exception.ConflictException;
import com.academy.project.demo.exception.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BadRequestException.class, NoSuchFieldException.class, NumberFormatException.class, JsonProcessingException.class, IllegalArgumentException.class, PropertyReferenceException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse runtime(RuntimeException exception) {
        log.info(exception.getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundHandler(NotFoundException notFoundException) {
        log.info(notFoundException.getMessage());
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), notFoundException.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse conflictHandler(ConflictException conflictException) {
        log.info(conflictException.getMessage());
        return new ErrorResponse(HttpStatus.CONFLICT.value(), conflictException.getMessage());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse httpClientErrorHandler(HttpClientErrorException httpClientErrorException) {
        log.info(httpClientErrorException.getMessage());
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), httpClientErrorException.getMessage());
    }
}
