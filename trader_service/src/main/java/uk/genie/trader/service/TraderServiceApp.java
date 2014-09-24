package uk.genie.trader.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class TraderServiceApp {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(TraderServiceApp.class, args);
    }
}