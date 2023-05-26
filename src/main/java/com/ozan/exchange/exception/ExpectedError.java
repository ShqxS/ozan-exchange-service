package com.ozan.exchange.exception;

import lombok.Getter;

@Getter
public abstract class ExpectedError extends RuntimeException {

    private final int code;
    private final String error;

    ExpectedError(int code, String error, String message) {
        super(message);
        this.code = code;
        this.error = error;
    }
}
