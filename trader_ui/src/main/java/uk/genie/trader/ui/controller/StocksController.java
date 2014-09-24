package uk.genie.trader.ui.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.genie.trader.ui.model.StockAlertRequest;
import uk.genie.trader.ui.mq.alerts.StockPriceAlertPublisher;
import uk.genie.trader.ui.services.StockService;

@Controller
@SuppressWarnings("UnusedDeclaration")
@RequestMapping("/rest/stock")
public class StocksController {

    private static final Logger logger = LoggerFactory.getLogger(StocksController.class);

    @Autowired
    StockPriceAlertPublisher stockPriceAlertPublisher;

    private final StockService stockService = new StockService();

    @RequestMapping(value = "/alerts", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getStockAlerts() throws IOException {
        logger.debug("getStockAlerts, REST API called");
        return new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(
                stockService.getStockAlertList());
    }

    @RequestMapping(value = "/prices", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getStockPrices() throws IOException {
        logger.debug("getStockPrices, REST API called");
        return new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(
                stockService.getStockPriceList());
    }

    @RequestMapping(value = "/alerts", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public void addStockAlert(@RequestBody String json) throws IOException {
        logger.debug("addStockAlert, REST API called, body '" + json + "'");

        // JSON string to StockAlertRequest and send to queue
        StockAlertRequest stockAlertRequest = new ObjectMapper().readValue(json, StockAlertRequest.class);
        stockPriceAlertPublisher.sendMessage(stockAlertRequest);
    }
}
