package com.demo.powerbi.powerbidemo.integration.powerbi.utility;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "integration.power-bi")
public class PowerBIProperties {

    private String clientId;

    private String tenantId;

    private String authorityUrl;

    private String apiScope;

    private String baseUrl;

    private String certificatePath;

}
