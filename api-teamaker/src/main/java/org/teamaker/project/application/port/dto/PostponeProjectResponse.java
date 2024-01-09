package org.teamaker.project.application.port.dto;

import java.time.LocalDate;
import java.util.Objects;

public record PostponeProjectResponse(String projectId, LocalDate newStartDate, LocalDate newEndDate) {
        public PostponeProjectResponse {
            Objects.requireNonNull(projectId);
            Objects.requireNonNull(newStartDate);
            Objects.requireNonNull(newEndDate);
        }
}
