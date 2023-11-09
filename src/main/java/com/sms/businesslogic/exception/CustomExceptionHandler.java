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

    @ExceptionHandler(value = {ProductOutOfStockException.class})
    public ResponseEntity<Object> handleProdcutOutOfStockException(
            ProductOutOfStockException exception
    ){
        HttpStatus httpStatus =HttpStatus.CONFLICT;
        CustomException customException = new CustomException(
                exception.getMessage(),
                httpStatus,
                ZonedDateTime.now());
        return new ResponseEntity<>(customException,httpStatus);
    }

    @ExceptionHandler(value = {OrderNotFoundException.class})
    public ResponseEntity<Object> handleOrderNotFoundException(
            OrderNotFoundException exception
    ){
        HttpStatus httpStatus =HttpStatus.CONFLICT;
        CustomException customException = new CustomException(
                exception.getMessage(),
                httpStatus,
                ZonedDateTime.now());
        return new ResponseEntity<>(customException,httpStatus);
    }

    @ExceptionHandler(value = {PaymentNotFoundException.class})
    public ResponseEntity<Object> handlePaymentNotFoundException(PaymentNotFoundException exception) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        CustomException customException = new CustomException(
                exception.getMessage(),
                httpStatus,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(customException,httpStatus);
    }
}

