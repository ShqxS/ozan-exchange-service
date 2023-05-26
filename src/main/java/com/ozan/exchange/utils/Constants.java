package com.ozan.exchange.utils;

public interface Constants {

    String LOG_CORRELATION_ID = "CORRELATION_ID";

    interface Error {
        int MONGO_DB_CONNECTION_ERROR = 1000;
        int TRANSACTION_NOT_FOUND = 1001;
        int TRANSACTION_ID_OR_DATE_NOT_PROVIDED = 1003;

    }

}
