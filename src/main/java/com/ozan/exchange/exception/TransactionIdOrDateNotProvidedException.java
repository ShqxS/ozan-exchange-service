package com.ozan.exchange.exception;


import com.ozan.exchange.utils.Constants;

public class TransactionIdOrDateNotProvidedException extends ExpectedError {

    public TransactionIdOrDateNotProvidedException(String message) {
        super(Constants.Error.TRANSACTION_NOT_FOUND, TransactionIdOrDateNotProvidedException.class.getName(), message);
    }
}
