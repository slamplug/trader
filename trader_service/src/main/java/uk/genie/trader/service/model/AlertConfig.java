package uk.genie.trader.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AlertConfig {

    @JsonProperty(value = "symbol")
    private String symbol;
    @JsonProperty(value = "alertBelow")
    private double lowBound;
    @JsonProperty(value = "alertAbove")
    private double highBound;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getLowBound() {
        return lowBound;
    }

    public void setLowBound(double lowBound) {
        this.lowBound = lowBound;
    }

    public double getHighBound() {
        return highBound;
    }

    public void setHighBound(double highBound) {
        this.highBound = highBound;
    }

    @Override
    public String toString() {
        return "AlertConfig{" +
                "symbol='" + symbol + '\'' +
                ", lowBound=" + lowBound +
                ", highBound=" + highBound +
                '}';
    }
}
