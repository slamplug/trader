package uk.genie.trader.ui.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.genie.trader.ui.db.StockRepository;
import uk.genie.trader.ui.model.StockAlert;
import uk.genie.trader.ui.services.StockService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebAppContext.class})
@WebAppConfiguration
public class StocksControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private StockService stockServicMock;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        StockRepository.getStockAlertList().clear();
    }

    @Test
    public void getStockAlertsFromRestApi() throws Exception {

        List<StockAlert> stockAlertList = new ArrayList<StockAlert>();
        StockAlert alert1 = new StockAlert();
        alert1.setSymbol("APPL");
        alert1.setOldPrice(100.34);
        alert1.setNewPrice(104.34);
        stockAlertList.add(alert1);

        StockRepository.getStockAlertList().add(alert1);

        mockMvc.perform(get("/rest/stock/alerts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.[0].symbol").value("APPL"))
                .andExpect(jsonPath("$.[0].oldPrice").value(100.34))
                .andExpect(jsonPath("$.[0].newPrice").value(104.34));
    }
}
