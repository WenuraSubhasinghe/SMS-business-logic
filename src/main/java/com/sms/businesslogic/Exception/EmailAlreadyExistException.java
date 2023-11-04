package com.sms.businesslogic.Exception;


public class EmailAlreadyExistException extends RuntimeException{

    public EmailAlreadyExistException(String message) {super(message);}

    public EmailAlreadyExistException(String message, Throwable cause) {super(message,cause);}
}
