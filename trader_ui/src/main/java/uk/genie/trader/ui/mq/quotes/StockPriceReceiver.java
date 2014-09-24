package uk.genie.trader.ui.mq.quotes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import uk.genie.trader.ui.db.StockRepository;
import uk.genie.trader.ui.mq.MqConstants;

import javax.jms.ConnectionFactory;
import java.io.IOException;

@Configuration
public class StockPriceReceiver {

    private static final Logger logger = LoggerFactory.getLogger(StockPriceReceiver.class);

    @Bean
    StockPriceReceiver receiver() {
        return new StockPriceReceiver();
    }


    @Bean
    SimpleMessageListenerContainer stockPriceListenerContainer(StockPriceReceiver receiver, ConnectionFactory connectionFactory) {
        MessageListenerAdapter messageListener = new MessageListenerAdapter(receiver);
        messageListener.setDefaultListenerMethod("receiveStockPriceMessage");

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setMessageListener(messageListener);
        container.setConnectionFactory(connectionFactory);
        container.setDurableSubscriptionName(MqConstants.STOCK_PRICES_TOPIC);
        container.setDestinationName(MqConstants.STOCK_PRICES_TOPIC);
        container.setPubSubDomain(true); // true = topic

        return container;
    }

    public void receiveStockPriceMessage(String message) throws IOException {
        logger.info("Received <" + message + ">");
        StockRepository.updateRepositoryList(message, MqConstants.STOCK_PRICES_TOPIC);
    }
}
