package uk.genie.trader.ui.mq.alerts;

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
public class StockPriceAlertReceiver {

    private static final Logger logger = LoggerFactory.getLogger(StockPriceAlertReceiver.class);

    @Bean
    StockPriceAlertReceiver receiver() {
        return new StockPriceAlertReceiver();
    }


    @Bean
    SimpleMessageListenerContainer stockAlertListenerContainer(StockPriceAlertReceiver receiver, ConnectionFactory connectionFactory) {
        MessageListenerAdapter messageListener = new MessageListenerAdapter(receiver);
        messageListener.setDefaultListenerMethod("receiveStockPriceAlertMessage");

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setMessageListener(messageListener);
        container.setConnectionFactory(connectionFactory);
        container.setDurableSubscriptionName(MqConstants.STOCK_PRICE_ALERTS_TOPIC);
        container.setDestinationName(MqConstants.STOCK_PRICE_ALERTS_TOPIC);
        container.setPubSubDomain(true); // true = topic

        return container;
    }

    public void receiveStockPriceAlertMessage(String message) throws IOException {
        logger.info("Received <" + message + ">");
        StockRepository.updateRepositoryList(message, MqConstants.STOCK_PRICE_ALERTS_TOPIC);
    }
}
