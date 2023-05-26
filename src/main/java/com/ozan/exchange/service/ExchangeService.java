package com.ozan.exchange.service;

import com.ozan.exchange.controller.request.ConvertedCurrenciesRequest;
import com.ozan.exchange.controller.request.CurrencyExchangeRequest;
import com.ozan.exchange.controller.request.ExchangeRateRequest;
import com.ozan.exchange.model.dto.CurrencyExchangeDTO;
import com.ozan.exchange.model.dto.ExchangeRateDTO;

import java.util.List;

public interface ExchangeService {

    ExchangeRateDTO getExchangeRate(ExchangeRateRequest exchangeRateRequest);

    CurrencyExchangeDTO getCurrencyExchange(CurrencyExchangeRequest currencyExchangeRequest);

    List<CurrencyExchangeDTO> getCurrencyExchanges(ConvertedCurrenciesRequest convertedCurrenciesRequest);

}
