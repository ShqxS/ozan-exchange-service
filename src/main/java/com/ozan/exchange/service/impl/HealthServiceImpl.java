package com.ozan.exchange.service.impl;

import com.ozan.exchange.controller.response.HealthResponse;
import com.ozan.exchange.exception.MongoDBConnectionRefusedException;
import com.ozan.exchange.service.HealthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.data.mongo.MongoHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HealthServiceImpl implements HealthService {

    private final HealthEndpoint healthEndpoint;

    private final MongoHealthIndicator mongoHealthIndicator;

    private final ApplicationAvailability applicationAvailability;

    public HealthResponse checkHealth() {
        Status status = healthEndpoint.health().getStatus();
        long currentTime = System.currentTimeMillis();
        log.debug("Health status {}", status);
        return new HealthResponse(status == Status.UP ? "OK" : status.getCode(), currentTime);
    }

    public HealthResponse checkLiveness() {
        LivenessState livenessState = applicationAvailability.getLivenessState();
        long currentTime = System.currentTimeMillis();
        log.debug("Liveness status {}", livenessState);
        return new HealthResponse(livenessState.name(), currentTime);
    }

    public HealthResponse checkReadiness() {
        ReadinessState readinessState = applicationAvailability.getReadinessState();
        long currentTime = System.currentTimeMillis();
        Health mongoHealth = mongoHealthIndicator.health();
        if (Status.UP != mongoHealth.getStatus()) {
            log.error("MongoDB is not ready({})", mongoHealth.getDetails());
            throw new MongoDBConnectionRefusedException("MongoDB is not ready");
        }
        log.debug("Readiness status: {}", readinessState);
        return new HealthResponse(readinessState.name(), currentTime);
    }
}
