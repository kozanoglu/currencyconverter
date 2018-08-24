package com.kozanoglu.currencyconverter.repository;

import com.kozanoglu.currencyconverter.repository.entity.ExchangeRate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM exchange_rate er ORDER BY timestamp DESC LIMIT 1")
    ExchangeRate getLatestRate();

    List<ExchangeRate> findByTimestampBetween(Long start, Long end);

}
