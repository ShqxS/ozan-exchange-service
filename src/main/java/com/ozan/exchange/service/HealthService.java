package com.ozan.exchange.service;


import com.ozan.exchange.controller.response.HealthResponse;

public interface HealthService {

    HealthResponse checkHealth();

    HealthResponse checkLiveness();

    HealthResponse checkReadiness();

}
