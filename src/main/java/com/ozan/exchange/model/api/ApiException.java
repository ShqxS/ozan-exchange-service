package com.ozan.exchange.model.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiException {

    private int code;
    private String error;
    private String message;

    public ApiException(int code, String error, String message) {
        this.code = code;
        this.error = error;
        this.message = message;
    }
}
