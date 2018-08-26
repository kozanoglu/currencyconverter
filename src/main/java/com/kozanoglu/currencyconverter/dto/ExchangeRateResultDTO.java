package com.kozanoglu.currencyconverter.dto;

import java.util.Date;

public class ExchangeRateResultDTO {

    private Double rate;

    private Date date;

    public ExchangeRateResultDTO() {
    }

    public ExchangeRateResultDTO(Double rate, Date date) {
        this.rate = rate;
        this.date = date;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
