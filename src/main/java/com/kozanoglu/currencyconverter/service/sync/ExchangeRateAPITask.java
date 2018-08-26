package com.kozanoglu.currencyconverter.service.sync;

import com.kozanoglu.currencyconverter.repository.ExchangeRateRepository;
import com.kozanoglu.currencyconverter.repository.entity.ExchangeRate;
import com.kozanoglu.currencyconverter.service.sync.api.ExchangeRateAPIClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

public class ExchangeRateAPITask extends TimerTask {

    private final Logger LOG = LoggerFactory.getLogger(ExchangeRateAPITask.class);

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final ExchangeRateRepository exchangeRateRepository;
    private final ExchangeRateAPIClient exchangeRateAPIClient;

    ExchangeRateAPITask(final ExchangeRateRepository exchangeRateRepository,
                        final ExchangeRateAPIClient exchangeRateAPIClient) {

        this.exchangeRateRepository = exchangeRateRepository;
        this.exchangeRateAPIClient = exchangeRateAPIClient;
    }

    public void run() {
        ExchangeRate rate = exchangeRateAPIClient.fetchRateFromAPI();
        if (rate != null) {
            LOG.info("EUR/USD rate is {} at {}", rate.getRate(), dateFormat.format(new Date(rate.getTimestamp())));
            exchangeRateRepository.save(rate);
        }
    }
}