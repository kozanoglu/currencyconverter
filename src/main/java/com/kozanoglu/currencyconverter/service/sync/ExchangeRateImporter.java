package com.kozanoglu.currencyconverter.service.sync;

import com.kozanoglu.currencyconverter.repository.ExchangeRateRepository;
import com.kozanoglu.currencyconverter.service.sync.api.ExchangeRateAPIClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Timer;

@Component
public class ExchangeRateImporter {

    private static final Logger LOG = LoggerFactory.getLogger(ExchangeRateImporter.class);
    private final ExchangeRateRepository exchangeRateRepository;
    private final ExchangeRateAPIClient exchangeRateAPIClient;

    @Value("${exchangerate.api.sync.period}")
    private Long exchangerateApiSyncPeriod;

    @Autowired
    public ExchangeRateImporter(final ExchangeRateRepository exchangeRateRepository, final ExchangeRateAPIClient exchangeRateAPIClient) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.exchangeRateAPIClient = exchangeRateAPIClient;
    }

    @PostConstruct
    public void scheduleTask() {
        LOG.info("Scheduling ExchangeRate API Task to run every {} seconds", exchangerateApiSyncPeriod);
        Timer time = new Timer();
        ExchangeRateAPITask st = new ExchangeRateAPITask(exchangeRateRepository, exchangeRateAPIClient);
        time.schedule(st, 0, exchangerateApiSyncPeriod * 1000);
    }
}
