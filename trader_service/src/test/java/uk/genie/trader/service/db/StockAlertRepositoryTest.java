package uk.genie.trader.service.db;

import org.junit.Before;
import org.junit.Test;
import uk.genie.trader.service.model.StockAlert;

import static org.junit.Assert.assertEquals;


public class StockAlertRepositoryTest {

    @Before
    public void testSetup() {
        StockAlertRepository.clearStockAlerts();
    }

    @Test
    public void testSymbolHasStockAlert1() throws Exception {
        StockAlertRepository.addStockAlert(createStockAlert("YHOO", 100.00, 101.00));

        assertEquals(true, StockAlertRepository.symbolHasStockAlert("YHOO"));
    }

    @Test
    public void testSymbolHasStockAlert2() throws Exception {
        StockAlertRepository.addStockAlert(createStockAlert("YHOO", 100.00, 101.00));

        assertEquals(false, StockAlertRepository.symbolHasStockAlert("MSFT"));

    }

    @Test
    public void testRemoveStockAlert() throws Exception {
        StockAlertRepository.addStockAlert(createStockAlert("YHOO", 100.00, 101.00));
        StockAlertRepository.addStockAlert(createStockAlert("MSFT", 100.00, 101.00));

        assertEquals(true, StockAlertRepository.symbolHasStockAlert("MSFT"));

        StockAlertRepository.removeStockAlert("MSFT");

        assertEquals(false, StockAlertRepository.symbolHasStockAlert("MSFT"));
    }

    @Test
    public void testgetCurrentStockAlerts() throws Exception {
        StockAlertRepository.addStockAlert(createStockAlert("YHOO", 100.00, 101.00));
        StockAlertRepository.addStockAlert(createStockAlert("MSFT", 100.00, 101.00));

        assertEquals(2, StockAlertRepository.getCurrentStockAlerts().size());
    }

    private StockAlert createStockAlert(String symbol, double oldPrice, double newPrice) {
        StockAlert stockAlert = new StockAlert();
        stockAlert.setSymbol(symbol);
        stockAlert.setOldPrice(oldPrice);
        stockAlert.setNewPrice(newPrice);
        return stockAlert;
    }
}
