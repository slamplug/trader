package uk.genie.trader.ui.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {
    private String symbol;
    @JsonProperty(value = "Name")
    private String name;
    @JsonProperty(value = "Ask")
    private double ask;
    @JsonProperty(value = "Currency")
    private String currency;
    @JsonProperty(value = "StockExchange")
    private String stockExchange;
    @JsonProperty(value = "previousAsk")
    private double previousAsk;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStockExchange() {
        return stockExchange;
    }

    public void setStockExchange(String stockExchange) {
        this.stockExchange = stockExchange;
    }

    public double getPreviousAsk() {
        return previousAsk;
    }

    public void setPreviousAsk(double previousAsk) {
        this.previousAsk = previousAsk;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", ask=" + ask +
                ", previousAsk=" + previousAsk +
                ", currency='" + currency + '\'' +
                ", stockExchange='" + stockExchange + '\'' +
                '}';
    }
}
