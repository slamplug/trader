package uk.genie.trader.service.db;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.genie.trader.service.model.StockAlert;

import java.util.Collection;
import java.util.HashMap;


public class StockAlertRepository {

    private static final Logger logger = LoggerFactory.getLogger(StockAlertRepository.class);

    private static final HashMap<String, StockAlert> currentStockAlerts = new HashMap<String, StockAlert>();

    public static void addStockAlert(StockAlert stockAlert) {
        logger.debug("add stock alert for symbol '" + stockAlert.getSymbol() + "'");
        synchronized (currentStockAlerts) {
            currentStockAlerts.put(stockAlert.getSymbol(), stockAlert);
        }
    }

    public static boolean symbolHasStockAlert(String symbol) {
        logger.debug("test for stock alert for symbol '" + symbol + "'");
        synchronized (currentStockAlerts) {
            return currentStockAlerts.containsKey(symbol);
        }
    }

    public static void removeStockAlert(String symbol) {
        logger.debug("remove stock alert for symbol '" + symbol + "'");
        synchronized (currentStockAlerts) {
            currentStockAlerts.remove(symbol);
        }
    }

    public static void clearStockAlerts() {
        logger.debug("clear stock alerts");
        currentStockAlerts.clear();
    }

    public static Collection<StockAlert> getCurrentStockAlerts() {
        synchronized (currentStockAlerts) {
            return currentStockAlerts.values();
        }
    }
}
