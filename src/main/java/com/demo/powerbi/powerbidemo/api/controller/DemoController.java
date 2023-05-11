package com.demo.powerbi.powerbidemo.api.controller;

import com.demo.powerbi.powerbidemo.integration.powerbi.dto.request.PowerBITokenRequest;
import com.demo.powerbi.powerbidemo.integration.powerbi.dto.response.PowerBIEmbedToken;
import com.demo.powerbi.powerbidemo.integration.powerbi.service.PowerBIService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class DemoController {

    private final PowerBIService powerBIService;

    @GetMapping("/")
    public String test() {
        return "UP";
    }

    @GetMapping("/token")
    public PowerBIEmbedToken generateToken() {
        // This is how URL usually looks, we can extract target workspace and report id
        String pbiUrl = "https://app.powerbi.com/groups/{this-is-workspace-id}/reports/{this-is-report-id}/ReportSection2fe2ff5b366cc0a2e6eb";

        return powerBIService.getToken(PowerBITokenRequest.builder()
                .datasets(Set.of("add-dataset-ids"))
                .reports(Set.of("add-report-ids"))
                .targetWorkspaces(Set.of("add-workspace-ids"))
                .build());
    }

}
