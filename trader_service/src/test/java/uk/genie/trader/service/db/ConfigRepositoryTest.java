package uk.genie.trader.service.db;

import org.junit.Before;
import org.junit.Test;
import uk.genie.trader.service.model.AlertConfig;

import static org.junit.Assert.assertEquals;

public class ConfigRepositoryTest {

    @Before
    public void testSetup() {
        ConfigRepository.getAlertConfigList().clear();
    }

    @Test
    public void testEmptyConfigRepository() {
        assertEquals(ConfigRepository.getAlertConfigList().size(), 0);
    }


    @Test
    public void addAlertConfigList() throws Exception {
        assertEquals(ConfigRepository.getAlertConfigList().size(), 0);
        ConfigRepository.addAlertConfig(createAlertConfig("GOOG", 100.00, 10.00));
        assertEquals(ConfigRepository.getAlertConfigList().size(), 1);
    }

    @Test
    public void addMultipleAlertConfigList() throws Exception {
        assertEquals(ConfigRepository.getAlertConfigList().size(), 0);
        ConfigRepository.addAlertConfig(createAlertConfig("GOOG", 100.00, 100.00));
        ConfigRepository.addAlertConfig(createAlertConfig("MSFT", 100.00, 100.00));
        assertEquals(ConfigRepository.getAlertConfigList().size(), 2);
    }

    @Test
    public void addMultipleAlertConfigList2() throws Exception {
        assertEquals(ConfigRepository.getAlertConfigList().size(), 0);
        ConfigRepository.addAlertConfig(createAlertConfig("GOOG", 100.00, 100.00));
        ConfigRepository.addAlertConfig(createAlertConfig("GOOG", 110.00, 112.00));
        assertEquals(ConfigRepository.getAlertConfigList().size(), 1);
        assertEquals(ConfigRepository.getAlertConfigList().get(0).getSymbol(), "GOOG");
        assertEquals(ConfigRepository.getAlertConfigList().get(0).getLowBound(), 110.00, 0.01);
        assertEquals(ConfigRepository.getAlertConfigList().get(0).getHighBound(), 112.00, 0.01);
    }

    private AlertConfig createAlertConfig(String symbol, double lowBound, double highBound) {
        AlertConfig alertConfig = new AlertConfig();
        alertConfig.setSymbol(symbol);
        alertConfig.setLowBound(lowBound);
        alertConfig.setHighBound(highBound);
        return alertConfig;
    }
}
