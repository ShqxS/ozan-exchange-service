package com.ozan.exchange.model.dto;

import java.time.LocalDateTime;

public record CurrencyExchangeDTO(
        String transactionId,
        String source,
        String target,
        int amount,
        double result,
        LocalDateTime createdAt) {
}
