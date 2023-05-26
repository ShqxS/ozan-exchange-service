package com.ozan.exchange;

import com.ozan.exchange.client.response.CurrencyConvertResponse;
import com.ozan.exchange.client.response.CurrencyExchangeRateResponse;
import com.ozan.exchange.controller.request.ConvertedCurrenciesRequest;
import com.ozan.exchange.controller.request.CurrencyExchangeRequest;
import com.ozan.exchange.controller.request.ExchangeRateRequest;
import com.ozan.exchange.controller.request.TransactionDateRange;
import com.ozan.exchange.domain.CurrencyExchange;
import com.ozan.exchange.model.dto.CurrencyExchangeDTO;
import com.ozan.exchange.model.dto.ExchangeRateDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestUtil {

    private TestUtil() {

    }

    public static ExchangeRateRequest createExchangeRateRequest() {

        return new ExchangeRateRequest("TRY", "EUR");
    }

    public static ExchangeRateDTO createExchangeRateDTO() {

        Map<String, Double> rates = new HashMap<>();
        rates.put("EUR", 0.045);

        return new ExchangeRateDTO(
                Boolean.TRUE,
                "TRY",
                LocalDate.of(2023,
                        Month.MAY, 25),
                rates);
    }

    public static CurrencyExchangeRequest createCurrencyExchangeRequest() {

        return new CurrencyExchangeRequest(
                "TRY",
                "EUR",
                15
                );
    }

    public static CurrencyExchangeDTO createCurrencyExchangeDTO() {

        return new CurrencyExchangeDTO(
                "64706bab7243f4508545ac96",
                "TRY",
                "EUR",
                15,
                0.675,
                LocalDateTime.of(2023,
                        Month.MAY, 25, 20, 30, 40)
        );
    }

    public static ConvertedCurrenciesRequest createConvertedCurrenciesRequestWithTransactionId() {

        return new ConvertedCurrenciesRequest(
                0,
                10,
                "64706bab7243f4508545ac96",
                null
        );
    }

    public static ConvertedCurrenciesRequest createConvertedCurrenciesRequestWithTimeRange() {

        return new ConvertedCurrenciesRequest(
                0,
                2,
                null,
                new TransactionDateRange(
                        LocalDateTime.of(2023,
                                Month.APRIL, 25, 20, 30, 40),
                        LocalDateTime.of(2023,
                                Month.JUNE, 25, 20, 30, 40)
                )
        );
    }

    public static ConvertedCurrenciesRequest createConvertedCurrenciesRequestWithNoTransactionIdAndTimeRange() {

        return new ConvertedCurrenciesRequest(
                0,
                10,
                null,
                null
        );
    }

    public static List<CurrencyExchangeDTO> createCurrencyExchangeDTOListWithOneEntity () {
        return List.of(
                new CurrencyExchangeDTO(
                        "64706bab7243f4508545ac96",
                        "TRY",
                        "EUR",
                        15,
                        0.675,
                        LocalDateTime.of(2023,
                                Month.MAY, 25, 20, 30, 40)
                )
        );
    }

    public static List<CurrencyExchange> createCurrencyExchangeListWithTwoEntity () {
        return List.of(
                new CurrencyExchange(
                        "64706bab7243f4508545ac96",
                        "TRY",
                        "EUR",
                        15,
                        0.675,
                        LocalDateTime.of(2023,
                                Month.MAY, 25, 20, 30, 40)
                ),
                new CurrencyExchange(
                        "64706bab7243f4508545ac97",
                        "TRY",
                        "USD",
                        10,
                        0.51,
                        LocalDateTime.of(2023,
                                Month.MAY, 26, 20, 30, 40)
                )
        );
    }

    public static CurrencyExchangeRateResponse createCurrencyExchangeRateResponse() {

        Map<String, Double> rates = new HashMap<>();
        rates.put("EUR", 0.045);

        return new CurrencyExchangeRateResponse(
                Boolean.TRUE,
                "TRY",
                LocalDate.of(2023,
                        Month.MAY, 25),
                rates);

    }

    public static CurrencyConvertResponse createCurrencyConvertResponse() {

        return new CurrencyConvertResponse(
                Boolean.TRUE,
                0.675
        );
    }

    public static CurrencyExchange createCurrencyExchange() {

        return new CurrencyExchange(
                "64706bab7243f4508545ac96",
                "TRY",
                "EUR",
                15,
                0.675,
                LocalDateTime.of(2023,
                        Month.MAY, 25, 20, 30, 40)
        );
    }

    public static List<CurrencyExchangeDTO> createCurrencyExchangeDTOListWithTwoEntity () {
        return List.of(
                new CurrencyExchangeDTO(
                        "64706bab7243f4508545ac96",
                        "TRY",
                        "EUR",
                        15,
                        0.675,
                        LocalDateTime.of(2023,
                                Month.MAY, 25, 20, 30, 40)
                ),
                new CurrencyExchangeDTO(
                        "64706bab7243f4508545ac97",
                        "TRY",
                        "USD",
                        10,
                        0.51,
                        LocalDateTime.of(2023,
                                Month.MAY, 26, 20, 30, 40)
                )
        );
    }
}
