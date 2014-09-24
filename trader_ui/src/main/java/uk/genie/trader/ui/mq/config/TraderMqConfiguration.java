package uk.genie.trader.ui.mq.config;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.ConnectionFactory;


@Configuration
public class TraderMqConfiguration {

    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    @Bean
    ConnectionFactory pooledConnectionFactory() {
        ActiveMQConnectionFactory amqConnectionFactory = new ActiveMQConnectionFactory(url);
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        pooledConnectionFactory.setConnectionFactory(amqConnectionFactory);
        return pooledConnectionFactory;
    }
}
