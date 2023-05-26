package com.ozan.exchange.client.feign;

import com.ozan.exchange.client.response.CurrencyConvertResponse;
import com.ozan.exchange.client.response.CurrencyExchangeRateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "currency-api-layer",
        url = "${microservice.currencyApiLayer}",
        configuration = FeignClientProperties.FeignClientConfiguration.class)
public interface CurrencyApiLayerFeignClient {

    String BASE_PATH = "exchangerates_data";

    @GetMapping(BASE_PATH + "/latest")
    CurrencyExchangeRateResponse getCurrencyLiveExchangeRate(@RequestParam String base, @RequestParam String symbols);

    @GetMapping(BASE_PATH + "/convert")
    CurrencyConvertResponse convert(@RequestParam String from, @RequestParam String to, @RequestParam int amount);
}
