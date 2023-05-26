package com.ozan.exchange.exception;


import com.ozan.exchange.utils.Constants;

public class MongoDBConnectionRefusedException extends NotExpectedError {

    public MongoDBConnectionRefusedException(String message) {
        super(Constants.Error.MONGO_DB_CONNECTION_ERROR, MongoDBConnectionRefusedException.class.getName(), message);
    }
}
