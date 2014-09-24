package uk.genie.trader.collect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class TraderCollectApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(TraderCollectApplication.class, args);
    }
}