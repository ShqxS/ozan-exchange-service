package com.ozan.exchange.configuration;

import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class FeignClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        Exception exception = new Default().decode(methodKey, response);

        if (exception instanceof RetryableException) {
            return exception;
        }

        if (HttpStatus.valueOf(response.status()).is5xxServerError()) {
            return new RetryableException(
                    response.status(),
                    "Server error:" + response.reason(),
                    response.request().httpMethod(),
                    null, response.request());
        }

        return exception;
    }

}
