package com.kozanoglu.currencyconverter.service.exchangerate;

import com.kozanoglu.currencyconverter.dto.ExchangeRateResultDTO;
import com.kozanoglu.currencyconverter.repository.ExchangeRateRepository;
import com.kozanoglu.currencyconverter.repository.entity.ExchangeRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExchangeRateService {

    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    public ExchangeRateService(final ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public ExchangeRateResultDTO getLatestCurrencyRate() {
        ExchangeRate latestRate = exchangeRateRepository.getLatestRate();
        return new ExchangeRateResultDTO(latestRate.getRate(), new Date(latestRate.getTimestamp()));
    }

    public List<ExchangeRateResultDTO> getCurrencyRatesBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        Date from = Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant());
        Date to = Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant());
        List<ExchangeRate> byTimestampBetween = exchangeRateRepository.findByTimestampBetween(from.getTime(), to.getTime());
        return byTimestampBetween.stream().map(t -> new ExchangeRateResultDTO(t.getRate(), new Date(t.getTimestamp()))).collect(Collectors.toList());
    }

}
