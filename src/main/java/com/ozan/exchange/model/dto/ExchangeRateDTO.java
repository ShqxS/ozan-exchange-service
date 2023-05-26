package com.ozan.exchange.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public record ExchangeRateDTO (boolean success, String base, LocalDate date, Map<String, Double> rates) {}
