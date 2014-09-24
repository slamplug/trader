package uk.genie.trader.collect.mq.quotes;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import uk.genie.trader.collect.model.Quote;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.genie.trader.collect.mq.MqConstants;

@Component("quoteResponseSender")
public class QuoteResponseSender {

    private static final Logger logger = LoggerFactory.getLogger(QuoteResponseSender.class);

    @Autowired
    @Qualifier("jmsTemplate")
    private JmsTemplate jmsTemplate;

    public void sendMessage(final List<Quote> quotes) {
        MessageCreator messageCreator = new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                String message = null;
                try {
                    message = new ObjectMapper().writer().withDefaultPrettyPrinter().
                            writeValueAsString(quotes);
                } catch (JsonProcessingException e) {
                    throw new JMSException(e.getMessage());
                }
                return session.createTextMessage(message);
            }
        };

        logger.debug("Sending message to Q '" + MqConstants.STOCK_PRICES_RESPONSE_COMP_Q + "'");
        jmsTemplate.send(MqConstants.STOCK_PRICES_RESPONSE_COMP_Q, messageCreator);
    }
}
