package uk.genie.trader.service.scheduled;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import uk.genie.trader.service.db.ConfigRepository;
import uk.genie.trader.service.model.QuoteRequest;
import uk.genie.trader.service.mq.quotes.QuoteRequestSender;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableScheduling
public class StockQuoteJob {

    private static final Logger logger = LoggerFactory.getLogger(StockQuoteJob.class);

    @Autowired
    QuoteRequestSender quoteRequestSender;

    @Scheduled(fixedRate = 5000)
    public void sendStocksQouteRequest() {
        logger.debug("Sending stock quote request");
        quoteRequestSender.sendMessage(createQuoteRequest(ConfigRepository.getQuoteSymbols()));
    }

    private QuoteRequest createQuoteRequest(List<String> symbolsList) {
        logger.debug("create quote request for symbols " + symbolsList);
        QuoteRequest quoteRequest = new QuoteRequest();
        String[] symbolsArr = new String[symbolsList.size()];
        for (int i = 0; i < symbolsList.size(); i++) {
            symbolsArr[i] = symbolsList.get(i);
        }
        logger.debug("Array '" + Arrays.toString(symbolsArr) + "'");
        quoteRequest.setSymbols(symbolsArr);
        return quoteRequest;
    }
}
