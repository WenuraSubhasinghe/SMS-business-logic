package com.sms.businesslogic.exception;

public class ProdcutOutOfStockException extends RuntimeException {

    public ProdcutOutOfStockException(String message) {super(message);}

    public ProdcutOutOfStockException(String message, Throwable cause) {super(message,cause);}

}
