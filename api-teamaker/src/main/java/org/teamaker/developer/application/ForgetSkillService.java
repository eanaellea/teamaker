package org.teamaker.developer.application;

import org.teamaker.developer.application.port.in.forgetSkill.ForgetSkillCommand;
import org.teamaker.developer.application.port.in.forgetSkill.ForgetSkillResponse;
import org.teamaker.developer.application.port.in.forgetSkill.ForgetSkillUseCase;
import org.teamaker.developer.application.port.out.removeSkill.RemoveSkillCommand;
import org.teamaker.developer.application.port.out.removeSkill.RemoveSkillPort;

import java.util.NoSuchElementException;

class ForgetSkillService implements ForgetSkillUseCase {
    private final RemoveSkillPort removeSkillPort;

    public ForgetSkillService(RemoveSkillPort removeSkillPort) {
        this.removeSkillPort = removeSkillPort;
    }

    public ForgetSkillResponse.Response forgetSkill(ForgetSkillCommand command) {
        try {
            removeSkillPort.removeSkill(new RemoveSkillCommand(command.getDeveloperId(), command.getSkillId()));
            return new ForgetSkillResponse.SuccessResponse("Skill successfully forgotten");
        } catch (NoSuchElementException exception) {
            return new ForgetSkillResponse.ErrorResponse(exception.getMessage());
        }
    }
}