package com.ozan.exchange.controller.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record ConvertedCurrenciesRequest(
        @NotNull
        @PositiveOrZero
        Integer page,
        @NotNull
        @PositiveOrZero
        Integer size,
        String transactionId,
        @Valid
        TransactionDateRange transactionDateRange
        ) {
}
