package org.teamaker.developer.application;

import org.springframework.stereotype.Component;
import org.teamaker.developer.application.port.in.getDevelopersByTechnology.GetDevelopersByTechnologyCommand;
import org.teamaker.developer.application.port.in.getDevelopersByTechnology.GetDevelopersByTechnologyResponse;
import org.teamaker.developer.application.port.in.getDevelopersByTechnology.GetDevelopersByTechnologyUseCase;
import org.teamaker.developer.application.port.out.findDevelopersByTechnology.FindDevelopersByTechnologyCommand;
import org.teamaker.developer.application.port.out.findDevelopersByTechnology.FindDevelopersByTechnologyPort;
import org.teamaker.developer.domain.Developer;
import org.teamaker.developer.domain.dto.GetDevelopersByTechnologyDtoResponse;

import java.util.List;
import java.util.NoSuchElementException;

@Component
class GetDevelopersByTechnologyService implements GetDevelopersByTechnologyUseCase {

    private final FindDevelopersByTechnologyPort findDevelopersByTechnologyPort;

    public GetDevelopersByTechnologyService(FindDevelopersByTechnologyPort findDevelopersByTechnologyPort) {
        this.findDevelopersByTechnologyPort = findDevelopersByTechnologyPort;
    }

    @Override
    public GetDevelopersByTechnologyResponse.Response getDevelopersByTechnology(GetDevelopersByTechnologyCommand command) {
        try {
            List<Developer> developers = findDevelopersByTechnologyPort.findDevelopersByTechnology(new FindDevelopersByTechnologyCommand(command.getTechnologyId()));

            return new GetDevelopersByTechnologyResponse.SuccessResponse(
                    new GetDevelopersByTechnologyDtoResponse(
                            command.getTechnologyId(),
                            developers
                                    .stream()
                                    .map(Developer::toResponse)
                                    .toList()
                    )
            );
        } catch (NoSuchElementException exception) {
            return new GetDevelopersByTechnologyResponse.ErrorResponse("Technology not found");
        }
    }

}
