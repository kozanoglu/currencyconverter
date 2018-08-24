package com.kozanoglu.currencyconverter.controller;

import com.kozanoglu.currencyconverter.repository.ExchangeRateRepository;
import com.kozanoglu.currencyconverter.repository.entity.ExchangeRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController()
public class CurrencyController {

    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    public CurrencyController(final ExchangeRateRepository exchangeRateRepository) {

        this.exchangeRateRepository = exchangeRateRepository;
    }

    @GetMapping(value = "/latest", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    ExchangeRate getLatestCurrencyRate() {
        return exchangeRateRepository.getLatestRate();
    }

    /**
     * Fetches the exchange rates between dates
     *
     * @param startDate "2018-08-24T01:30:00.000-05:00"
     * @param endDate   "2018-28-25T01:30:00.000-05:00"
     * @return List of exchage rates ordered by time
     */
    @GetMapping(value = "/between", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    List<ExchangeRate> getCurrencyRatesBetweenDates(@RequestParam("startDate")
                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                            LocalDateTime startDate,
                                                    @RequestParam("endDate")
                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                            LocalDateTime endDate) {

        Date from = Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant());
        Date to = Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant());

        return exchangeRateRepository.findByTimestampBetween(from.getTime(), to.getTime());
    }
}
