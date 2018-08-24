package com.kozanoglu.currencyconverter.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ExchangeRate {

    @Id
    @Column(name = "timestamp")
    private Long timestamp;

    @Column(name = "rate")
    private Double rate;

    public ExchangeRate() {
    }

    public ExchangeRate(Long timestamp, Double rate) {
        this.timestamp = timestamp;
        this.rate = rate;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
