package com.design.order.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentLogger implements CommandLineRunner {
    @Override
    public void run(String... args) {
        System.out.println("New Relic API Key: " + System.getenv("NEW_RELIC_API_KEY"));
        System.out.println("New Relic Account ID: " + System.getenv("NEW_RELIC_ACCOUNT_ID"));
    }
}
