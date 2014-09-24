package uk.genie.trader.service.mq.quotes;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.genie.trader.service.model.QuoteRequest;
import uk.genie.trader.service.mq.MqConstants;

@Component("quoteRequestSender")
public class QuoteRequestSender {

    private static final Logger logger = LoggerFactory.getLogger(QuoteRequestSender.class);

    @Autowired
    @Qualifier("jmsTemplate")
    private JmsTemplate jmsTemplate;

    public void sendMessage(final QuoteRequest quoteRequest) {
        MessageCreator messageCreator = new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                String message = null;
                try {
                    message = new ObjectMapper().writer().withDefaultPrettyPrinter().
                            writeValueAsString(quoteRequest);
                } catch (JsonProcessingException e) {
                    throw new JMSException(e.getMessage());
                }
                return session.createTextMessage(message);
            }
        };

        logger.debug("Sending message to Q '" + MqConstants.STOCK_PRICES_REQUEST_Q + "'");
        jmsTemplate.send(MqConstants.STOCK_PRICES_REQUEST_Q, messageCreator);
    }
}
