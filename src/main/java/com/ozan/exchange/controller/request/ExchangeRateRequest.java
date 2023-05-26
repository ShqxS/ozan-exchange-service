package com.ozan.exchange.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ExchangeRateRequest(
        @NotBlank
        @Size(min = 3, max = 3)
        String base,
        @NotBlank
        @Size(min = 3, max = 3)
        String target) {
}
