package com.ozan.exchange.service.impl;

import com.ozan.exchange.client.feign.CurrencyApiLayerFeignClient;
import com.ozan.exchange.client.response.CurrencyConvertResponse;
import com.ozan.exchange.client.response.CurrencyExchangeRateResponse;
import com.ozan.exchange.controller.request.ConvertedCurrenciesRequest;
import com.ozan.exchange.controller.request.CurrencyExchangeRequest;
import com.ozan.exchange.controller.request.ExchangeRateRequest;
import com.ozan.exchange.controller.request.TransactionDateRange;
import com.ozan.exchange.domain.CurrencyExchange;
import com.ozan.exchange.exception.TransactionIdOrDateNotProvidedException;
import com.ozan.exchange.exception.TransactionNotFoundException;
import com.ozan.exchange.mapper.ExchangeMapper;
import com.ozan.exchange.model.dto.CurrencyExchangeDTO;
import com.ozan.exchange.model.dto.ExchangeRateDTO;
import com.ozan.exchange.repository.ExchangeRepository;
import com.ozan.exchange.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private final ExchangeMapper exchangeMapper;
    private final ExchangeRepository exchangeRepository;

    private final CurrencyApiLayerFeignClient currencyApiLayerFeignClient;
    @Override
    public ExchangeRateDTO getExchangeRate(ExchangeRateRequest exchangeRateRequest) {

        CurrencyExchangeRateResponse currencyExchangeRateResponse = currencyApiLayerFeignClient.getCurrencyLiveExchangeRate(exchangeRateRequest.base(), exchangeRateRequest.target());

        return exchangeMapper.liveDataResponseToRateDTO(currencyExchangeRateResponse);
    }

    @Override
    public CurrencyExchangeDTO getCurrencyExchange(CurrencyExchangeRequest currencyExchangeRequest) {

        CurrencyConvertResponse currencyConvertResponse = currencyApiLayerFeignClient.convert(currencyExchangeRequest.source(), currencyExchangeRequest.target(), currencyExchangeRequest.amount());

        CurrencyExchange currencyExchange = exchangeRepository.save(CurrencyExchange.builder()
                        .source(currencyExchangeRequest.source())
                        .target(currencyExchangeRequest.target())
                        .amount(currencyExchangeRequest.amount())
                        .result(currencyConvertResponse.result())
                .build());

        return exchangeMapper.currencyExchangeEntityToDto(currencyExchange);

    }

    @Override
    public List<CurrencyExchangeDTO> getCurrencyExchanges(ConvertedCurrenciesRequest convertedCurrenciesRequest) {

        if (Objects.nonNull(convertedCurrenciesRequest.transactionId())) {
            return List.of(getCurrencyExchangeByTransactionId(convertedCurrenciesRequest.transactionId()));
        } else if (Objects.nonNull(convertedCurrenciesRequest.transactionDateRange())) {
            return getCurrencyExchangeByDateRange(convertedCurrenciesRequest.page(), convertedCurrenciesRequest.size(), convertedCurrenciesRequest.transactionDateRange());
        }

        throw new TransactionIdOrDateNotProvidedException("Transaction id or transaction date range must be provided");
    }

    private CurrencyExchangeDTO getCurrencyExchangeByTransactionId(String transactionId) {
        CurrencyExchange currencyExchange = exchangeRepository.findByTransactionId(transactionId)
                .orElseThrow(getTransactionNotFoundExceptionSupplier(transactionId));

        return exchangeMapper.currencyExchangeEntityToDto(currencyExchange);
    }

    private List<CurrencyExchangeDTO> getCurrencyExchangeByDateRange(Integer page, Integer size, TransactionDateRange transactionDateRange) {
        List<CurrencyExchange> allExchangesByTimeRange = exchangeRepository.findAllExchangesByTimeRange(page, size, transactionDateRange.startDate(), transactionDateRange.endDate());

        return exchangeMapper.currencyExchangeEntityListToDtoList(allExchangesByTimeRange);
    }

    private Supplier<TransactionNotFoundException> getTransactionNotFoundExceptionSupplier(
            String transactionId) {
        return () ->
                new TransactionNotFoundException(
                        String.format("Transaction with id: <%s> not found", transactionId));
    }

}
