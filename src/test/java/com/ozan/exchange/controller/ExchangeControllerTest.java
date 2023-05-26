package com.ozan.exchange.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozan.exchange.TestUtil;
import com.ozan.exchange.controller.request.ConvertedCurrenciesRequest;
import com.ozan.exchange.controller.request.CurrencyExchangeRequest;
import com.ozan.exchange.controller.request.ExchangeRateRequest;
import com.ozan.exchange.service.ExchangeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ExchangeController.class)
public class ExchangeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeService exchangeService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void itShouldGetExchangeRates() throws Exception {

        // Given
        ExchangeRateRequest exchangeRateRequest = TestUtil.createExchangeRateRequest();

        // When
        when(exchangeService.getExchangeRate(exchangeRateRequest)).thenReturn(TestUtil.createExchangeRateDTO());

        ResultActions resultActions = mockMvc.perform(post("/v1/exchange/rate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exchangeRateRequest)));

        // Then
        resultActions.andExpect(status().isOk()).andExpect(content().json(
                """
                    {
                      "data": {
                        "success": true,
                        "base": "TRY",
                        "date": "2023-05-25",
                        "rates": {
                            "EUR": 0.045
                        }
                      }
                    }
                """
        ));
    }

    @Test
    public void itShouldGetCurrencyExchange() throws Exception {

        // Given
        CurrencyExchangeRequest currencyExchangeRequest = TestUtil.createCurrencyExchangeRequest();

        // When
        when(exchangeService.getCurrencyExchange(currencyExchangeRequest)).thenReturn(TestUtil.createCurrencyExchangeDTO());

        ResultActions resultActions = mockMvc.perform(post("/v1/exchange")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(currencyExchangeRequest)));

        // Then
        resultActions.andExpect(status().isOk()).andExpect(content().json(
                """
                    {
                      "data": {
                        "transactionId": "64706bab7243f4508545ac96",
                        "source": "TRY",
                        "target": "EUR",
                        "amount": 15,
                        "result": 0.675,
                        "createdAt":"2023-05-25T20:30:40"
                      }
                    }
                """
        ));
    }

    @Test
    public void itShouldGetCurrencyExchangeByTransactionId() throws Exception {

        // Given
        ConvertedCurrenciesRequest convertedCurrenciesRequest = TestUtil.createConvertedCurrenciesRequestWithTransactionId();

        // When
        when(exchangeService.getCurrencyExchanges(convertedCurrenciesRequest)).thenReturn(TestUtil.createCurrencyExchangeDTOListWithOneEntity());

        ResultActions resultActions = mockMvc.perform(post("/v1/exchange/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(convertedCurrenciesRequest)));
        // Then
        resultActions.andExpect(status().isOk()).andExpect(content().json(
                """
                            {
                                "data": [
                                    {
                                        "transactionId": "64706bab7243f4508545ac96",
                                        "source": "TRY",
                                        "target": "EUR",
                                        "amount": 15,
                                        "result": 0.675,
                                        "createdAt": "2023-05-25T20:30:40"
                                    }
                                ]
                            }
                        """
        ));

    }


    @Test
    public void itShouldGetCurrencyExchangesByTimeRange() throws Exception {

        // Given
        ConvertedCurrenciesRequest convertedCurrenciesRequest = TestUtil.createConvertedCurrenciesRequestWithTimeRange();

        // When
        when(exchangeService.getCurrencyExchanges(convertedCurrenciesRequest)).thenReturn(TestUtil.createCurrencyExchangeDTOListWithTwoEntity());

        ResultActions resultActions = mockMvc.perform(post("/v1/exchange/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                                "page": 0,
                                "size": 2,
                                "transactionDateRange": {
                                    "startDate": "2023-04-25T20:30:40",
                                    "endDate": "2023-06-25T20:30:40"
                                }
                            }
                        """));
        // Then
        resultActions.andExpect(status().isOk()).andExpect(content().json(
                """
                            {
                                "data": [
                                    {
                                        "transactionId": "64706bab7243f4508545ac96",
                                        "source": "TRY",
                                        "target": "EUR",
                                        "amount": 15,
                                        "result": 0.675,
                                        "createdAt": "2023-05-25T20:30:40"
                                    },
                                    {
                                        "transactionId": "64706bab7243f4508545ac97",
                                        "source": "TRY",
                                        "target": "USD",
                                        "amount": 10,
                                        "result": 0.51,
                                        "createdAt": "2023-05-26T20:30:40"
                                    }
                                ]
                            }
                        """
        ));
    }

}
