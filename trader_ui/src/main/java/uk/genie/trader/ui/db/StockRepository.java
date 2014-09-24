package uk.genie.trader.ui.db;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.genie.trader.ui.model.Quote;
import uk.genie.trader.ui.model.StockAlert;
import uk.genie.trader.ui.mq.MqConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StockRepository {

    private static List<Quote> quoteList = new ArrayList<Quote>();

    private static List<StockAlert> stockAlertList = new ArrayList<StockAlert>();

    public static void updateRepositoryList(String jsonString, String type) throws IOException {
        if (type.equals(MqConstants.STOCK_PRICE_ALERTS_TOPIC)) {
            updateAlertsList(jsonString);
        } else {
            updateQuotesList(jsonString);
        }
    }

    private static void updateAlertsList(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        StockAlert[] stockAlerts = mapper.readValue(jsonString, StockAlert[].class);

        for (StockAlert stockAlert : stockAlerts) {
            if (stockAlert.getNewPrice() != -99.00) {
                upsertAlert(stockAlert);
            } else {
                removeAlert(stockAlert);
            }
        }
    }

    private static void upsertAlert(final StockAlert newAlert) {
        synchronized (stockAlertList) {
            StockAlert matched = null;
            for (StockAlert currentAlert : stockAlertList) { // is it current list
                if (currentAlert.getSymbol().equals(newAlert.getSymbol())) {
                    matched = currentAlert;
                    break;
                }
            }
            if (matched != null) { // if in currently then remove current alert
                stockAlertList.remove(matched);
            }
            stockAlertList.add(newAlert); // add updated alerts
        }
    }

    private synchronized static void removeAlert(final StockAlert newAlert) {
        synchronized (stockAlertList) {
            StockAlert matched = null;
            for (StockAlert currentAlert : stockAlertList) { // is it current list
                if (currentAlert.getSymbol().equals(newAlert.getSymbol())) {
                    matched = currentAlert;
                    break;
                }
            }
            if (matched != null) { // if in currently then remove current alert
                stockAlertList.remove(matched);
            }
        }
    }

    private static void updateQuotesList(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        Quote[] quotes = mapper.readValue(jsonString, Quote[].class);

        for (Quote quote : quotes) {
            upsertQuote(quote);
        }
    }

    private synchronized static void upsertQuote(final Quote newQuote) {
        Quote matched = null;
        for (Quote currentQuote : quoteList) { // is it current list
            if (currentQuote.getSymbol().equals(newQuote.getSymbol())) {
                matched = currentQuote;
                break;
            }
        }
        if (matched != null) { // if in currently then remove current alert
            quoteList.remove(matched);
        }
        quoteList.add(newQuote); // add updated alerts
    }

    public static List<Quote> getQuoteList() {
        return quoteList;
    }

    public static List<StockAlert> getStockAlertList() {
        return stockAlertList;
    }
}
