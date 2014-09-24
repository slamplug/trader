package uk.genie.trader.service.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.genie.trader.service.model.AlertConfig;

import java.util.ArrayList;
import java.util.List;


public class ConfigRepository {

    private static final Logger logger = LoggerFactory.getLogger(ConfigRepository.class);

    private static List<String> quoteSymbols;
    private static List<AlertConfig> alertConfigList = new ArrayList<>();

    static {
        quoteSymbols = new ArrayList<>();
        quoteSymbols.add("AAPL");
        quoteSymbols.add("YHOO");
        quoteSymbols.add("MSFT");
        quoteSymbols.add("GOOGL");
    }

    public static void addQuoteSymbol(String symbol) {
        if (!quoteSymbols.contains(symbol)) {
            quoteSymbols.add(symbol);
        }
    }

    public static List<String> getQuoteSymbols() {
        return quoteSymbols;
    }

    public synchronized static void addAlertConfig(AlertConfig newAlert) {
        logger.debug("add alert to config " + newAlert);
        AlertConfig matched = null;
        for (AlertConfig currentAlert : alertConfigList) { // is it current list
            if (currentAlert.getSymbol().equals(newAlert.getSymbol())) {
                matched = currentAlert;
                break;
            }
        }
        if (matched != null) { // if in currently then remove current alert
            alertConfigList.remove(matched);
        }
        alertConfigList.add(newAlert); // add updated alerts
    }

    public static List<AlertConfig> getAlertConfigList() {
        logger.debug("get all alert onfigs, returning " + alertConfigList);
        return alertConfigList;
    }

    public static AlertConfig getAlertConfig(String symbol) {
        logger.debug("get alert config for symbol '" + symbol + "'");
        for (AlertConfig currentAlert : alertConfigList) { // is it current list
            if (currentAlert.getSymbol().equals(symbol)) {
                return currentAlert;
            }
        }
        return null;
    }
}
