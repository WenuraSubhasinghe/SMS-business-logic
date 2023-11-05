package com.sms.businesslogic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = {EmailAlreadyExistException.class})
    public ResponseEntity<Object> handleEmailNotFoundException(
            EmailAlreadyExistException exception
    ){
        HttpStatus httpStatus =HttpStatus.CONFLICT;
        CustomException customException = new CustomException(
                exception.getMessage(),
                httpStatus,
                ZonedDateTime.now());
        return new ResponseEntity<>(customException,httpStatus);
    }

    @ExceptionHandler(value = {EmailOrPasswordIncorrectException.class})
    public ResponseEntity<Object> handleEmailOrPasswordIncorrectException(
            EmailOrPasswordIncorrectException exception
    ){
        HttpStatus httpStatus =HttpStatus.FORBIDDEN;
        CustomException customException = new CustomException(
                exception.getMessage(),
                httpStatus,
                ZonedDateTime.now());
        return new ResponseEntity<>(customException,httpStatus);
    }

    @ExceptionHandler(value = {ProdcutOutOfStockException.class})
    public ResponseEntity<Object> handleProdcutOutOfStockException(
            ProdcutOutOfStockException exception
    ){
        HttpStatus httpStatus =HttpStatus.CONFLICT;
        CustomException customException = new CustomException(
                exception.getMessage(),
                httpStatus,
                ZonedDateTime.now());
        return new ResponseEntity<>(customException,httpStatus);
    }





}

