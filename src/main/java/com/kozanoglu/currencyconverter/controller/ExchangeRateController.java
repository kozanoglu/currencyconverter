package com.kozanoglu.currencyconverter.controller;

import com.kozanoglu.currencyconverter.dto.ExchangeRateResultDTO;
import com.kozanoglu.currencyconverter.service.exchangerate.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController()
@RequestMapping("/api")
public class ExchangeRateController {

    private ExchangeRateService exchangeRateService;

    @Autowired
    public ExchangeRateController(final ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    /**
     * Fetches the latest available EURUSD value from db
     *
     * @return ExchangeRate
     */
    @GetMapping(value = "/latest", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    ExchangeRateResultDTO getLatestCurrencyRate() {
        return exchangeRateService.getLatestCurrencyRate();
    }

    /**
     * Fetches the exchange rates between dates
     *
     * @param startDate "2018-08-24T01:30:00.000-05:00"
     * @param endDate   "2018-28-25T01:30:00.000-05:00"
     * @return List of exchange rates ordered by time
     */
    @GetMapping(value = "/between", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    List<ExchangeRateResultDTO> getCurrencyRatesBetweenDates(@RequestParam("startDate")
                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                            LocalDateTime startDate,
                                                    @RequestParam("endDate")
                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                            LocalDateTime endDate) {

        return exchangeRateService.getCurrencyRatesBetweenDates(startDate, endDate);
    }
}
