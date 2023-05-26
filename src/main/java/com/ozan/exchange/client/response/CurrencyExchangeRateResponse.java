package com.ozan.exchange.client.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public record CurrencyExchangeRateResponse(boolean success, String base, LocalDate date, Map<String, Double> rates) {}
