package com.edstrom.WigellMcRental.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("http://localhost:8587")
    private String serviceUrl;

    @Bean
    public WebClient converterClient() {return WebClient.create(serviceUrl);}

}
