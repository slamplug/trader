package uk.genie.trader.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
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
