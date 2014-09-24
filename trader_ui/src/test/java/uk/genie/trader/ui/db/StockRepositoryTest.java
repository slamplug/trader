package uk.genie.trader.ui.db;

import org.junit.Before;
import org.junit.Test;
import uk.genie.trader.ui.mq.MqConstants;

import static org.junit.Assert.assertEquals;

public class StockRepositoryTest {

    @Before
    public void testSetup() {
        StockRepository.getQuoteList().clear();
        StockRepository.getStockAlertList().clear();
    }

    @Test
    public void testEmptyStockPricesList() {
        assertEquals(StockRepository.getQuoteList().size(), 0);
    }

    @Test
    public void testEmptyStockAlertList() {
        assertEquals(StockRepository.getStockAlertList().size(), 0);
    }

    @Test
    public void addStockAlertToList() throws Exception {
        assertEquals(StockRepository.getStockAlertList().size(), 0);
        StockRepository.updateRepositoryList("[ { \"symbol\" : \"GOOG\" , \"oldPrice\" : 23.4 , \"newPrice\" : 44.4 } ]", MqConstants.STOCK_PRICE_ALERTS_TOPIC);
        assertEquals(StockRepository.getStockAlertList().size(), 1);
    }

    @Test
    public void addMultipleUniqueStockAlertToList() throws Exception {
        assertEquals(StockRepository.getStockAlertList().size(), 0);
        StockRepository.updateRepositoryList("[{ \"symbol\" : \"GOOG\" , \"oldPrice\" : 23.4 , \"newPrice\" : 44.4 }]", MqConstants.STOCK_PRICE_ALERTS_TOPIC);
        StockRepository.updateRepositoryList("[{ \"symbol\" : \"YHOO\" , \"oldPrice\" : 23.4 , \"newPrice\" : 44.4 }]", MqConstants.STOCK_PRICE_ALERTS_TOPIC);
        assertEquals(StockRepository.getStockAlertList().size(), 2);
    }

    @Test
    public void addMultipleStockAlertToList() throws Exception {
        assertEquals(StockRepository.getStockAlertList().size(), 0);
        StockRepository.updateRepositoryList("[{ \"symbol\" : \"GOOG\" , \"oldPrice\" : 23.4 , \"newPrice\" : 44.4 }]", MqConstants.STOCK_PRICE_ALERTS_TOPIC);
        StockRepository.updateRepositoryList("[{ \"symbol\" : \"GOOG\" , \"oldPrice\" : 33.4 , \"newPrice\" : 55.4 }]", MqConstants.STOCK_PRICE_ALERTS_TOPIC);
        assertEquals(StockRepository.getStockAlertList().size(), 1);
        assertEquals(StockRepository.getStockAlertList().get(0).getOldPrice(), 33.4, 0.01);
        assertEquals(StockRepository.getStockAlertList().get(0).getNewPrice(), 55.4, 0.01);
    }
}
