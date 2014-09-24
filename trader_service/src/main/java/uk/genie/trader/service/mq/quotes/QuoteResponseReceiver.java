package uk.genie.trader.service.mq.quotes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;

import javax.jms.ConnectionFactory;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import uk.genie.trader.service.db.QuoteRepository;
import uk.genie.trader.service.model.Quote;
import uk.genie.trader.service.model.StockAlert;
import uk.genie.trader.service.mq.MqConstants;
import uk.genie.trader.service.mq.alerts.StockPriceAlertPublisher;
import uk.genie.trader.service.services.StockPriceAlertCalculator;

@Configuration
public class QuoteResponseReceiver {

    private static final Logger logger = LoggerFactory.getLogger(QuoteResponseReceiver.class);

    @Bean
    QuoteResponseReceiver receiver() {
        return new QuoteResponseReceiver();
    }

    @Autowired
    StockPriceAlertPublisher stockPriceAlertPublisher;

    @Bean
    SimpleMessageListenerContainer quoteListenerContainer(QuoteResponseReceiver receiver, ConnectionFactory connectionFactory) {
        MessageListenerAdapter messageListener = new MessageListenerAdapter(receiver);
        messageListener.setDefaultListenerMethod("receiveQuoteResponseMessage");

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setMessageListener(messageListener);
        container.setConnectionFactory(connectionFactory);
        container.setDestinationName(MqConstants.STOCK_PRICES_RESPONSE_Q);
        return container;
    }

    public void receiveQuoteResponseMessage(String message) throws IOException {
        logger.debug("Received <" + message + ">");
        Quote[] quotes = new ObjectMapper().readValue(message, Quote[].class);
        logger.debug("recieved '" + quotes.length +
                "' quotes '" + Arrays.toString(quotes) + "'");

        // store in memory repo
        QuoteRepository.upsert(Arrays.asList(quotes));

        // calculate alerts
        List<StockAlert> stockAlertList = new StockPriceAlertCalculator().calculateAlerts(Arrays.asList(quotes));

        // publish alerts
        stockPriceAlertPublisher.sendMessage(stockAlertList);
    }
}
