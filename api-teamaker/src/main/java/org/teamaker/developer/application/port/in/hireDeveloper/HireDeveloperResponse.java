package org.teamaker.developer.application.port.in.hireDeveloper;

import org.teamaker.developer.domain.dto.DeveloperResponse;
public class HireDeveloperResponse {
    public interface Response {}

    public record SuccessResponse(DeveloperResponse developer) implements Response {}

    public record ErrorResponse(String errorMessage) implements Response {}

}
