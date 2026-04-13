package com.edstrom.WigellMcRental.service;


import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;


@Service
public class PricingService {

    private final WebClient converterClient;
    private final String PATH = "/api/currency/rate";

    public PricingService(WebClient converterClient) {
        this.converterClient = converterClient;
    }

    public BigDecimal getRate(String from, String to) {
        return converterClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(PATH)
                        .queryParam("from", from)
                        .queryParam("to", to)
                        .build())
                .retrieve()
                .bodyToMono(BigDecimal.class)
                .block();
    }
}