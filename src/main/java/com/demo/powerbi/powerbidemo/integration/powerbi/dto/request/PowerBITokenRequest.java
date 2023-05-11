package com.demo.powerbi.powerbidemo.integration.powerbi.dto.request;

import com.demo.powerbi.powerbidemo.integration.powerbi.dto.IdWrapper;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

@Getter
public class PowerBITokenRequest {

    private final Set<IdWrapper> datasets;
    private final Set<IdWrapper> reports;
    private final Set<IdWrapper> targetWorkspaces;

    /**
     * Private, must be instantiated with builder.
     */
    private PowerBITokenRequest(Set<IdWrapper> datasets, Set<IdWrapper> reports, Set<IdWrapper> targetWorkspaces) {
        this.datasets = datasets;
        this.reports = reports;
        this.targetWorkspaces = targetWorkspaces;
    }

    /**
     * Builder pattern in order to wrap {@link IdWrapper} creation.
     *
     * @return Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Set<IdWrapper> datasets = new HashSet<>();
        private final Set<IdWrapper> reports = new HashSet<>();
        private final Set<IdWrapper> targetWorkspaces = new HashSet<>();

        public Builder datasets(Set<String> datasetIds) {
            datasetIds.forEach(datasetId -> datasets.add(new IdWrapper(datasetId)));
            return this;
        }

        public Builder reports(Set<String> reportIds) {
            reportIds.forEach(reportId -> reports.add(new IdWrapper(reportId)));
            return this;
        }

        public Builder targetWorkspaces(Set<String> targetWorkspaceIds) {
            targetWorkspaceIds.forEach(targetWorkspaceId -> targetWorkspaces.add(new IdWrapper(targetWorkspaceId)));
            return this;
        }

        public PowerBITokenRequest build() {
            return new PowerBITokenRequest(datasets, reports, targetWorkspaces);
        }
    }


}
