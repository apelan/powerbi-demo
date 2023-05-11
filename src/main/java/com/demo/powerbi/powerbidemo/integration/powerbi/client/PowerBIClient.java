package com.demo.powerbi.powerbidemo.integration.powerbi.client;

import com.demo.powerbi.powerbidemo.integration.powerbi.dto.request.PowerBITokenRequest;
import com.demo.powerbi.powerbidemo.integration.powerbi.dto.response.PowerBIEmbedToken;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PowerBIClient {

    private final WebClient powerBiClient;

    public PowerBIClient(@Qualifier("powerBiClient") WebClient powerBiClient) {
        this.powerBiClient = powerBiClient;
    }

    public PowerBIEmbedToken getAccessToken(String accessToken, PowerBITokenRequest powerBITokenRequest) {
        log.info("Generating access token for reports {}", powerBITokenRequest.getReports());
        return powerBiClient
                .post()
                .uri("/GenerateToken")
                .body(BodyInserters.fromValue(powerBITokenRequest))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(PowerBIEmbedToken.class)
                .block();
    }


}
