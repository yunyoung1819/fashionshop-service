package com.shop.backoffice.exception.model.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    private String reason;
    private String errorMessage;
}
