package com.ozan.exchange.mapper;


import com.ozan.exchange.client.response.CurrencyExchangeRateResponse;
import com.ozan.exchange.domain.CurrencyExchange;
import com.ozan.exchange.model.dto.CurrencyExchangeDTO;
import com.ozan.exchange.model.dto.ExchangeRateDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExchangeMapper {

    ExchangeRateDTO liveDataResponseToRateDTO(CurrencyExchangeRateResponse currencyLiveDataResponse);


    CurrencyExchangeDTO currencyExchangeEntityToDto(CurrencyExchange currencyExchange);

    List<CurrencyExchangeDTO> currencyExchangeEntityListToDtoList(List<CurrencyExchange> currencyExchangeList);

}
