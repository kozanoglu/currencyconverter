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

    private static final String BASE_API_URL = "https://v3.exchangerate-api.com/bulk/";

    @Value("${exchangerate.api.url}")
    private String baseApiUrl;

    @Value("${exchangerate.api.key}")
    private String apiKey;

    private static final Logger LOG = LoggerFactory.getLogger(ExchangeRateImporter.class);

    public ExchangeRate fetchRateFromAPI() {

        try {

            String fullUrl = BASE_API_URL + apiKey + "/EUR";

            Date now = new Date();

            URL url = new URL(fullUrl);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject jsonobj = root.getAsJsonObject();

            JsonObject rates = jsonobj.get("rates").getAsJsonObject();
            double rate = rates.get("USD").getAsDouble();
            return new ExchangeRate(now.getTime(), rate);
        } catch (Exception e) {
            LOG.error("Failed to fetch the exchange rate from external API", e);
        }

        return null;
    }

}
