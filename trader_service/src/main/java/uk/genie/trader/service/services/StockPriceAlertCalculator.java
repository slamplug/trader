package uk.genie.trader.service.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.genie.trader.service.db.ConfigRepository;
import uk.genie.trader.service.db.StockAlertRepository;
import uk.genie.trader.service.model.AlertConfig;
import uk.genie.trader.service.model.Quote;
import uk.genie.trader.service.model.StockAlert;

import java.util.ArrayList;
import java.util.List;

public class StockPriceAlertCalculator {

    private static final Logger logger = LoggerFactory.getLogger(StockPriceAlertCalculator.class);

    public List<StockAlert> calculateAlerts(List<Quote> quotes) {
        logger.debug("calculate stock alerts, alert config " + ConfigRepository.getAlertConfigList());
        List<StockAlert> stockAlerts = new ArrayList<>();
        for (Quote quote: quotes) {
            AlertConfig config = ConfigRepository.getAlertConfig(quote.getSymbol());
            if (config == null) { // stock alert may have been removed
                if (StockAlertRepository.symbolHasStockAlert(quote.getSymbol())) {
                    StockAlertRepository.removeStockAlert(quote.getSymbol());
                    stockAlerts.add(createStockAlert(quote.getSymbol(), -99.00, -99.00));
                }
            } else { // alert config found, test quote against alert limits
                if (quote.getAsk() >= config.getHighBound() || quote.getAsk() <= config.getLowBound()) { // breached limits
                    if (StockAlertRepository.symbolHasStockAlert(quote.getSymbol())) {
                        StockAlertRepository.removeStockAlert(quote.getSymbol()); // remove current alert
                    }
                    logger.debug("'" + quote.getAsk() + "' exceeded bounds '" + config.getLowBound() + " <-> " + config.getHighBound() + "'");
                    StockAlert stockAlert = createStockAlert(quote.getSymbol(), quote.getPreviousAsk(), quote.getAsk());
                    stockAlerts.add(stockAlert); // create replacement alert
                    StockAlertRepository.addStockAlert(stockAlert);
                } else { // not breached limits, may be a previous alert to remove
                    if (StockAlertRepository.symbolHasStockAlert(quote.getSymbol())) {
                        StockAlertRepository.removeStockAlert(quote.getSymbol());
                        stockAlerts.add(createStockAlert(quote.getSymbol(), -99.00, -99.00));
                    }
                }
            }
        }
        logger.debug("stock alerts to send " + stockAlerts);
        return stockAlerts;
    }

    private StockAlert createStockAlert(String symbol, double oldPrice, double newPrice) {
        logger.debug("create stock alert from symbol '"  + symbol + "', newPrice '" + newPrice + "'");
        StockAlert stockAlert = new StockAlert();
        stockAlert.setSymbol(symbol);
        stockAlert.setOldPrice(oldPrice);
        stockAlert.setNewPrice(newPrice);
        return stockAlert;
    }
}
