package uk.genie.trader.collect.mq.quotes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import uk.genie.trader.collect.model.Quote;
import uk.genie.trader.collect.model.QuoteRequest;
import uk.genie.trader.collect.mq.MqConstants;
import uk.genie.trader.collect.rest.YahooQuoteServiceConsumer;

import javax.jms.ConnectionFactory;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Configuration
public class QuoteRequestReceiver {

    private static final Logger logger = LoggerFactory.getLogger(QuoteRequestReceiver.class);

    @Autowired
    QuoteResponseSender quoteResponseSender;

    @Bean
    QuoteRequestReceiver receiver() {
        return new QuoteRequestReceiver();
    }

    @Bean
    MessageListenerAdapter adapter(QuoteRequestReceiver receiver) {
        MessageListenerAdapter messageListener = new MessageListenerAdapter(receiver);
        messageListener.setDefaultListenerMethod("receiveMessage");
        return messageListener;
    }

    @Bean
    SimpleMessageListenerContainer container(MessageListenerAdapter messageListener, ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setMessageListener(messageListener);
        container.setConnectionFactory(connectionFactory);
        container.setDestinationName(MqConstants.STOCK_PRICES_REQUEST_Q);
        return container;
    }

    public void receiveMessage(String message) throws IOException {
        logger.debug("Received <" + message + ">");
        QuoteRequest request = new ObjectMapper().readValue(message, QuoteRequest.class);
        logger.debug("request <" + request + ">");

        List<Quote> quotes = new YahooQuoteServiceConsumer().getQuotes(
                Arrays.asList(request.getSymbols()));
        logger.debug("quotes <" + quotes + ">");

        quoteResponseSender.sendMessage(quotes);
    }
}
