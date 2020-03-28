package com.example.consumingrest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ConsumingRestApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumingRestApplication.class);
    private static final String RANDOM_QUOTES_URL = "https://gturnquist-quoters.cfapps.io/api/random";

    public static void main(String[] args) {
        SpringApplication.run(ConsumingRestApplication.class, args).close();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) {
        return args -> {
            Quote quote = restTemplate.getForObject(RANDOM_QUOTES_URL, Quote.class);
            if (quote == null) return;
            LOGGER.info(quote.toString());
        };
    }
}