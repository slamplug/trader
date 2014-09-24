package uk.genie.trader.service.mq.alerts;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import uk.genie.trader.service.db.ConfigRepository;
import uk.genie.trader.service.model.AlertConfig;
import uk.genie.trader.service.mq.MqConstants;

import javax.jms.ConnectionFactory;
import java.io.IOException;
import java.util.Arrays;

@Configuration
public class StockPriceAlertRequestReceiver {

    private static final Logger logger = LoggerFactory.getLogger(StockPriceAlertRequestReceiver.class);

    @Bean
    StockPriceAlertRequestReceiver receiver() {
        return new StockPriceAlertRequestReceiver();
    }

    @Bean
    SimpleMessageListenerContainer alertListenerContainer(StockPriceAlertRequestReceiver receiver, ConnectionFactory connectionFactory) {
        MessageListenerAdapter messageListener = new MessageListenerAdapter(receiver);
        messageListener.setDefaultListenerMethod("receiveStockAlertMessage");

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setMessageListener(messageListener);
        container.setConnectionFactory(connectionFactory);
        container.setDestinationName(MqConstants.STOCK_PRICE_ALERT_REQUEST_Q);
        return container;
    }

    public void receiveStockAlertMessage(String message) throws IOException {
        logger.debug("Received <" + message + ">");
        AlertConfig alertConfig = new ObjectMapper().readValue(message, AlertConfig.class);
        logger.debug("recieved alert config '" + alertConfig + "'");

        ConfigRepository.addAlertConfig(alertConfig);
        ConfigRepository.addQuoteSymbol(alertConfig.getSymbol());
    }
}
