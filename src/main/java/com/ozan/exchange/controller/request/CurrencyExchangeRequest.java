package com.ozan.exchange.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CurrencyExchangeRequest (
        @NotBlank
        @Size(min = 3, max = 3)
        String source,
        @NotBlank
        @Size(min = 3, max = 3)
        String target,
        @Positive
        int amount) {
}
