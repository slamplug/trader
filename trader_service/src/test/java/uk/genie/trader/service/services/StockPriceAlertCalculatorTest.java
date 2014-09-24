package uk.genie.trader.service.services;


import org.junit.Before;
import org.junit.Test;
import uk.genie.trader.service.db.ConfigRepository;
import uk.genie.trader.service.model.AlertConfig;
import uk.genie.trader.service.model.Quote;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StockPriceAlertCalculatorTest {

    @Before
    public void testSetup() {
        ConfigRepository.getAlertConfigList().clear();
    }

    @Test
    public void testEmptyAlertConfigList() {
        StockPriceAlertCalculator calculator = new StockPriceAlertCalculator();

        List<Quote> quotes = new ArrayList<>();
        quotes.add(createQuote("AAPL", "Apple", 100.06, "USD", 99.05));
        quotes.add(createQuote("GOOG", "Google", 120.56, "USD", 131.34));

        assertEquals(0, calculator.calculateAlerts(quotes).size());
    }

    @Test
    public void testLowerBoundAlert() {
        StockPriceAlertCalculator calculator = new StockPriceAlertCalculator();

        ConfigRepository.addAlertConfig(createAlertConfig("AAPL", 99.85, 102.34)); // no alert low < current ask and high > current ask
        ConfigRepository.addAlertConfig(createAlertConfig("GOOG", 120.56, 130.56)); // alert low = current ask and high > current ask
        ConfigRepository.addAlertConfig(createAlertConfig("AAPL", 112.51, 120.51)); // alert low > current ask and high > current ask

        List<Quote> quotes = new ArrayList<>();
        quotes.add(createQuote("AAPL", "Apple", 100.06, "USD", 99.05));
        quotes.add(createQuote("GOOG", "Google", 120.56, "USD", 131.34));
        quotes.add(createQuote("MSFT", "Microsoft", 110.51, "USD", 116.82));

        assertEquals(2, calculator.calculateAlerts(quotes).size());
    }

    @Test
    public void testUpperBoundAlert() {
        StockPriceAlertCalculator calculator = new StockPriceAlertCalculator();

        ConfigRepository.addAlertConfig(createAlertConfig("AAPL", 90.85, 112.34)); // no alert low < current ask and high > current ask
        ConfigRepository.addAlertConfig(createAlertConfig("GOOG", 110.56, 120.56)); // alert low < current ask and high = current ask
        ConfigRepository.addAlertConfig(createAlertConfig("AAPL", 102.51, 114.82)); // alert low < current ask and high < current ask

        List<Quote> quotes = new ArrayList<>();
        quotes.add(createQuote("AAPL", "Apple", 100.06, "USD", 99.05));
        quotes.add(createQuote("GOOG", "Google", 120.56, "USD", 131.34));
        quotes.add(createQuote("MSFT", "Microsoft", 110.51, "USD", 116.82));

        assertEquals(2, calculator.calculateAlerts(quotes).size());
    }

    private Quote createQuote(String symbol, String name, double ask, String currency, double previousAsk) {
        Quote quote = new Quote();
        quote.setSymbol(symbol);
        quote.setName(name);
        quote.setAsk(ask);
        quote.setCurrency(currency);
        quote.setPreviousAsk(previousAsk);
        return quote;
    }

    private AlertConfig createAlertConfig(String symbol, double lowBound, double highBound) {
        AlertConfig alertConfig = new AlertConfig();
        alertConfig.setSymbol(symbol);
        alertConfig.setLowBound(lowBound);
        alertConfig.setHighBound(highBound);
        return alertConfig;
    }
}
