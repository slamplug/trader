package uk.genie.trader.ui.model;


public class StockAlertRequest {

    private String symbol;
    private double alertBelow;
    private double alertAbove;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getAlertBelow() {
        return alertBelow;
    }

    public void setAlertBelow(double alertBelow) {
        this.alertBelow = alertBelow;
    }

    public double getAlertAbove() {
        return alertAbove;
    }

    public void setAlertAbove(double alertAbove) {
        this.alertAbove = alertAbove;
    }
}
