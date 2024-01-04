package org.teamaker.developer.application.port.out;

import org.teamaker.shared.validation.SelfValidating;

import javax.validation.constraints.NotNull;

public class FindDevelopersByTechnologyCommand extends SelfValidating<FindDevelopersByTechnologyCommand> {

        @NotNull
        private final String technologyId;

        public FindDevelopersByTechnologyCommand(String technologyId) {
            this.technologyId = technologyId;

            this.validateSelf();
        }

        public String getTechnology() {
            return technologyId;
        }
}
