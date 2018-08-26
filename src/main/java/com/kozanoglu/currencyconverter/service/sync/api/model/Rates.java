package com.kozanoglu.currencyconverter.service.sync.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rates {

    @JsonProperty("USD")
    double usd;

    public Rates() {
    }

    public double getUsd() {
        return usd;
    }

    public void setUsd(double usd) {
        this.usd = usd;
    }
}
