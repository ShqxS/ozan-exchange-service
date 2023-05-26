package com.ozan.exchange.exception;

import com.ozan.exchange.utils.Constants;

public class TransactionNotFoundException extends ExpectedError {

    public TransactionNotFoundException(String message) {
        super(Constants.Error.TRANSACTION_NOT_FOUND, TransactionNotFoundException.class.getName(), message);
    }
}
