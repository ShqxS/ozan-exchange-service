package com.ozan.exchange.service;

import com.ozan.exchange.TestUtil;
import com.ozan.exchange.client.feign.CurrencyApiLayerFeignClient;
import com.ozan.exchange.client.response.CurrencyConvertResponse;
import com.ozan.exchange.client.response.CurrencyExchangeRateResponse;
import com.ozan.exchange.controller.request.ConvertedCurrenciesRequest;
import com.ozan.exchange.controller.request.CurrencyExchangeRequest;
import com.ozan.exchange.controller.request.ExchangeRateRequest;
import com.ozan.exchange.domain.CurrencyExchange;
import com.ozan.exchange.exception.TransactionIdOrDateNotProvidedException;
import com.ozan.exchange.mapper.ExchangeMapper;
import com.ozan.exchange.model.dto.CurrencyExchangeDTO;
import com.ozan.exchange.model.dto.ExchangeRateDTO;
import com.ozan.exchange.repository.ExchangeRepository;
import com.ozan.exchange.service.impl.ExchangeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExchangeServiceTest {

    @InjectMocks
    private ExchangeServiceImpl underTest;
    @Mock
    private ExchangeMapper exchangeMapperMock;
    @Mock
    private ExchangeRepository exchangeRepositoryMock;
    @Mock
    private CurrencyApiLayerFeignClient currencyApiLayerFeignClientMock;

    @Test
    public void itShouldGetExchangeRates() {

        // Given
        ExchangeRateRequest exchangeRateRequest = TestUtil.createExchangeRateRequest();

        // When
        when(exchangeMapperMock.liveDataResponseToRateDTO(any(CurrencyExchangeRateResponse.class))).thenReturn(TestUtil.createExchangeRateDTO());
        when(currencyApiLayerFeignClientMock.getCurrencyLiveExchangeRate(exchangeRateRequest.base(), exchangeRateRequest.target())).thenReturn(TestUtil.createCurrencyExchangeRateResponse());
        ExchangeRateDTO exchangeRateDTO = underTest.getExchangeRate(exchangeRateRequest);

        // Then
        assertEquals(exchangeRateDTO.base(), exchangeRateRequest.base());
        assertTrue(exchangeRateDTO.rates().containsKey(exchangeRateRequest.target()));
    }

    @Test
    public void itShouldGetCurrencyExchange() {

        // Given
        CurrencyExchangeRequest currencyExchangeRequest = TestUtil.createCurrencyExchangeRequest();
        CurrencyConvertResponse currencyConvertResponse = TestUtil.createCurrencyConvertResponse();

        // When
        when(exchangeMapperMock.currencyExchangeEntityToDto(any(CurrencyExchange.class))).thenReturn(TestUtil.createCurrencyExchangeDTO());
        when(currencyApiLayerFeignClientMock.convert(currencyExchangeRequest.source(), currencyExchangeRequest.target(), currencyExchangeRequest.amount())).thenReturn(currencyConvertResponse);
        when(exchangeRepositoryMock.save(any(CurrencyExchange.class))).thenReturn(TestUtil.createCurrencyExchange());
        CurrencyExchangeDTO currencyExchangeDTO = underTest.getCurrencyExchange(currencyExchangeRequest);

        // Then
        assertEquals(currencyExchangeDTO.source(), currencyExchangeRequest.source());
        assertEquals(currencyExchangeDTO.result(), currencyConvertResponse.result());

    }

    @Test
    public void itShouldGetCurrencyExchangeByTransactionId() {


        // Given
        ConvertedCurrenciesRequest convertedCurrenciesRequest = TestUtil.createConvertedCurrenciesRequestWithTransactionId();

        // When
        when(exchangeRepositoryMock.findByTransactionId(convertedCurrenciesRequest.transactionId())).thenReturn(Optional.of(TestUtil.createCurrencyExchange()));
        when(exchangeMapperMock.currencyExchangeEntityToDto(any(CurrencyExchange.class))).thenReturn(TestUtil.createCurrencyExchangeDTO());
        List<CurrencyExchangeDTO> currencyExchanges = underTest.getCurrencyExchanges(convertedCurrenciesRequest);

        // Then
        for (CurrencyExchangeDTO currencyExchangeDTO : currencyExchanges) {
            assertEquals(currencyExchangeDTO.transactionId(), convertedCurrenciesRequest.transactionId());
        }
    }

    @Test
    public void itShouldGetCurrencyExchangeByTimeRange() {
        // Given
        ConvertedCurrenciesRequest convertedCurrenciesRequest = TestUtil.createConvertedCurrenciesRequestWithTimeRange();

        // When
        when(exchangeRepositoryMock.findAllExchangesByTimeRange(convertedCurrenciesRequest.page(), convertedCurrenciesRequest.size(), convertedCurrenciesRequest.transactionDateRange().startDate(), convertedCurrenciesRequest.transactionDateRange().endDate())).thenReturn(TestUtil.createCurrencyExchangeListWithTwoEntity());
        when(exchangeMapperMock.currencyExchangeEntityListToDtoList(any())).thenReturn(TestUtil.createCurrencyExchangeDTOListWithTwoEntity());
        List<CurrencyExchangeDTO> currencyExchanges = underTest.getCurrencyExchanges(convertedCurrenciesRequest);

        // Then
        assertEquals(currencyExchanges.size(), convertedCurrenciesRequest.size());
    }

    @Test
    public void itShouldThrowsExceptionWhenTransactionIdNotFound() {

        // Given
        ConvertedCurrenciesRequest convertedCurrenciesRequest = TestUtil.createConvertedCurrenciesRequestWithNoTransactionIdAndTimeRange();

        // When
        TransactionIdOrDateNotProvidedException thrown = assertThrows(
                TransactionIdOrDateNotProvidedException.class,
                () -> underTest.getCurrencyExchanges(convertedCurrenciesRequest)
        );

        // Then
        assertTrue(thrown.getMessage().contains("Transaction id or transaction date range must be provided"));

    }

}
