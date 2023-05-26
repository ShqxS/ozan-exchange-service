package com.ozan.exchange.configuration;

import feign.RetryableException;
import feign.Retryer;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@NoArgsConstructor
public class CustomRetryerConfiguration implements Retryer {

    private long period;

    private long maxPeriod;

    private int maxAttempts;

    private int attempt = 1;

    public CustomRetryerConfiguration(long period, long maxPeriod, int maxAttempts) {
        this.period = period;
        this.maxPeriod = maxPeriod;
        this.maxAttempts = maxAttempts;
    }

    @Override
    public void continueOrPropagate(RetryableException e) {

        log.error("Feign Retry Attempt {} due to {}.", attempt, e.getMessage());

        if (attempt++ == maxAttempts) {
            throw e;
        }
        long interval = nextMaxInterval();
        try {
            Thread.sleep(interval);
        } catch (InterruptedException exc) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public Retryer clone() {
        return new CustomRetryerConfiguration(500L, TimeUnit.SECONDS.toMillis(1L), 3);
    }

    private long nextMaxInterval() {
        long interval = (long) ((double) this.period * Math.pow(1.5D, this.attempt - 1));
        return Math.min(interval, this.maxPeriod);
    }
}
