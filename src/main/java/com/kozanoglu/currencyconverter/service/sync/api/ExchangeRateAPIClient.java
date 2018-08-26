package com.kozanoglu.currencyconverter.service.sync.api;

import com.kozanoglu.currencyconverter.repository.entity.ExchangeRate;
import com.kozanoglu.currencyconverter.service.sync.api.model.ResultWrapper;
import com.kozanoglu.currencyconverter.service.sync.ExchangeRateImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeRateAPIClient {

    @Value("${exchangerate.api.url}")
    private String baseApiUrl;

    @Value("${exchangerate.api.key}")
    private String apiKey;

    private static final Logger LOG = LoggerFactory.getLogger(ExchangeRateImporter.class);

    public ExchangeRate fetchRateFromAPI() {

        ExchangeRate result = null;
        try {

            String fullUrl = baseApiUrl + apiKey + "/EUR";
            RestTemplate restTemplate = new RestTemplate();
            final ResultWrapper response = restTemplate.getForObject(fullUrl, ResultWrapper.class);
            result = new ExchangeRate(response.getTimestamp() * 1000, response.getRates().getUsd());
        } catch (Exception e) {
            LOG.error("Failed to fetch the exchange rate from external API", e);
        }
        return result;
    }
}
