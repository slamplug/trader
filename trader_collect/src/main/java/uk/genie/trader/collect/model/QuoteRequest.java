package uk.genie.trader.collect.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class QuoteRequest {

    @JsonProperty(value = "symbols")
    private String[] symbols;

    public String[] getSymbols() {
        return symbols;
    }

    public void setSymbols(String[] symbols) {
        this.symbols = symbols;
    }

    @Override
    public String toString() {
        return "QuoteRequest{" +
                "symbols=" + Arrays.toString(symbols) +
                '}';
    }
}
