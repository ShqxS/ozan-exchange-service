package com.ozan.exchange.controller;

import com.ozan.exchange.controller.response.HealthResponse;
import com.ozan.exchange.service.HealthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HealthController {

    private final HealthService healthService;

    @GetMapping("/health")
    public HealthResponse healthCheck() {
        return healthService.checkHealth();
    }

    @GetMapping(value = "/liveness")
    public HealthResponse livenessCheck() {
        return healthService.checkLiveness();
    }

    @GetMapping(value = "/readiness")
    public HealthResponse readinessCheck() {
        return healthService.checkReadiness();
    }

}
