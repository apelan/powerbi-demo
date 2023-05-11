package com.demo.powerbi.powerbidemo.integration.powerbi.config;

import com.demo.powerbi.powerbidemo.integration.powerbi.utility.PowerBIProperties;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@Configuration("powerBiConfig")
@RequiredArgsConstructor
public class BeanConfig {

    private final PowerBIProperties powerBIProperties;

    @Bean
    @Qualifier("powerBiClient")
    public WebClient createPowerBIClient() {
        return WebClient.builder()
                .baseUrl(powerBIProperties.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

}
