package com.ozan.exchange.controller;

import com.ozan.exchange.controller.request.ConvertedCurrenciesRequest;
import com.ozan.exchange.controller.request.CurrencyExchangeRequest;
import com.ozan.exchange.controller.request.ExchangeRateRequest;
import com.ozan.exchange.model.api.ApiResponse;
import com.ozan.exchange.model.dto.CurrencyExchangeDTO;
import com.ozan.exchange.model.dto.ExchangeRateDTO;
import com.ozan.exchange.service.ExchangeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/v1/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @PostMapping(value = "/rate")
    public ApiResponse<ExchangeRateDTO> getExchangeRate(@RequestBody @Valid ExchangeRateRequest exchangeRateRequest) {
        log.info("Exchange Rate request received. {}", exchangeRateRequest);
        return ApiResponse.success(exchangeService.getExchangeRate(exchangeRateRequest));
    }


    @PostMapping
    public ApiResponse<CurrencyExchangeDTO> getCurrencyExchange(@RequestBody @Valid CurrencyExchangeRequest currencyExchangeRequest) {
        log.info("Currency Exchange request received. {}", currencyExchangeRequest);
        return ApiResponse.success(exchangeService.getCurrencyExchange(currencyExchangeRequest));
    }


    @PostMapping(value = "/transactions")
    public ApiResponse<List<CurrencyExchangeDTO>> getCurrencyExchangeByTransactionIdOrTimeRange(@RequestBody @Valid ConvertedCurrenciesRequest convertedCurrenciesRequest) {
        log.info("Get Transactions by transactionId or time range request received.");
        return ApiResponse.success(exchangeService.getCurrencyExchanges(convertedCurrenciesRequest));
    }

}
