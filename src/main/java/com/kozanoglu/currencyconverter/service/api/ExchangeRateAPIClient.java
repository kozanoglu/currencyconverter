package com.kozanoglu.currencyconverter.service.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kozanoglu.currencyconverter.repository.entity.ExchangeRate;
import com.kozanoglu.currencyconverter.service.sync.ExchangeRateImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

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

            Date now = new Date();

            URL url = new URL(fullUrl);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            JsonElement root = new JsonParser().parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject rates = root.getAsJsonObject().get("rates").getAsJsonObject();
            double rate = rates.get("USD").getAsDouble();
            result = new ExchangeRate(now.getTime(), rate);
        } catch (Exception e) {
            LOG.error("Failed to fetch the exchange rate from external API", e);
        }

        return result;
    }

}
