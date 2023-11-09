package com.sms.businesslogic.dto;

public class DeleteResponse {
    private boolean success;
    private String message;

    public DeleteResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
