package com.sms.businesslogic.exception;

public class EmailOrPasswordIncorrectException extends RuntimeException{

    public EmailOrPasswordIncorrectException(String message) {super(message);}

    public EmailOrPasswordIncorrectException(String message, Throwable cause) {super(message,cause);}
}
