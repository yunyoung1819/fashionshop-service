package com.shop.customer.common.exception.dto;


public class ErrorResponse {
    private String reason;
    private String errorMessage;

    public ErrorResponse(String reason, String errorMessage) {
        this.reason = reason;
        this.errorMessage = errorMessage;
    }

    public String getReason() {
        return reason;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
