package com.api.eventmanagement.exception;

import com.api.eventmanagement.dtos.ErrorDto;
import com.api.eventmanagement.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorDto> handleBadRequestException(BadRequestException badRequestException) {

    return new ResponseEntity<>(ErrorDto.builder().statusCode(HttpStatus.BAD_REQUEST.value())
        .message(badRequestException.getMessage()).build(), HttpStatus.BAD_REQUEST);
  }


  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDto> handleGeneralException(Exception exception) {

    return new ResponseEntity<>(ErrorDto.builder().statusCode(HttpStatus.BAD_REQUEST.value())
        .message(exception.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
