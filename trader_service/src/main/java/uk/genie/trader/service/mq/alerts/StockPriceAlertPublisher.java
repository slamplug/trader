package uk.genie.trader.service.mq.alerts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import uk.genie.trader.service.model.StockAlert;
import uk.genie.trader.service.mq.MqConstants;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Arrays;
import java.util.List;

@Component("stockPriceAlertPublisher")
public class StockPriceAlertPublisher {

    private static final Logger logger = LoggerFactory.getLogger(StockPriceAlertPublisher.class);

    @Autowired
    @Qualifier("jmsTemplate")
    private JmsTemplate jmsTemplate;

    public void sendMessage(final List<StockAlert> stockAlertList) {
        MessageCreator messageCreator = new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                String message = null;
                try {
                    message = new ObjectMapper().writer().withDefaultPrettyPrinter().
                            writeValueAsString(stockAlertList);
                } catch (JsonProcessingException e) {
                    throw new JMSException(e.getMessage());
                }
                return session.createTextMessage(message);
            }
        };

        logger.debug("Sending message to Q '" + MqConstants.STOCK_PRICE_ALERTS_COMP_Q + "'");
        jmsTemplate.send(MqConstants.STOCK_PRICE_ALERTS_COMP_Q, messageCreator);
    }
}
