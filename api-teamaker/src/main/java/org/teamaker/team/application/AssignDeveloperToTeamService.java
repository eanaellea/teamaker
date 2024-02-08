package org.teamaker.team.application;

import org.springframework.stereotype.Component;
import org.teamaker.developer.application.port.out.loadDeveloper.LoadDeveloperCommand;
import org.teamaker.developer.application.port.out.loadDeveloper.LoadDeveloperPort;
import org.teamaker.developer.application.port.out.loadDeveloperProjects.LoadDeveloperProjectsCommand;
import org.teamaker.developer.application.port.out.loadDeveloperProjects.LoadDeveloperProjectsPort;
import org.teamaker.developer.domain.Developer;
import org.teamaker.project.application.port.out.loadProject.LoadProjectCommand;
import org.teamaker.project.application.port.out.loadProject.LoadProjectPort;
import org.teamaker.project.domain.Project;
import org.teamaker.team.application.port.in.assignDeveloperToTeam.AssignDeveloperToTeamCommand;
import org.teamaker.team.application.port.in.assignDeveloperToTeam.AssignDeveloperToTeamResponse;
import org.teamaker.team.application.port.in.assignDeveloperToTeam.AssignDeveloperToTeamUseCase;
import org.teamaker.team.application.port.out.loadTeam.LoadTeamCommand;
import org.teamaker.team.application.port.out.loadTeam.LoadTeamPort;
import org.teamaker.team.application.port.out.saveTeam.SaveTeamCommand;
import org.teamaker.team.application.port.out.saveTeam.SaveTeamPort;
import org.teamaker.team.domain.Team;

import java.util.List;

@Component
public class AssignDeveloperToTeamService implements AssignDeveloperToTeamUseCase {
    private final LoadDeveloperPort loadDeveloperPort;
    private final LoadProjectPort loadProjectPort;
    private final LoadDeveloperProjectsPort loadDeveloperProjectsPort;
    private final LoadTeamPort loadTeamPort;
    private final SaveTeamPort saveTeamPort;

    public AssignDeveloperToTeamService(LoadDeveloperPort loadDeveloperPort, LoadProjectPort loadProjectPort, LoadDeveloperProjectsPort loadDeveloperProjectsPort, LoadTeamPort loadTeamPort, SaveTeamPort saveTeamPort) {
        this.loadDeveloperPort = loadDeveloperPort;
        this.loadProjectPort = loadProjectPort;
        this.loadDeveloperProjectsPort = loadDeveloperProjectsPort;
        this.loadTeamPort = loadTeamPort;
        this.saveTeamPort = saveTeamPort;
    }

    @Override
    public AssignDeveloperToTeamResponse.Response assignDeveloperToTeam(AssignDeveloperToTeamCommand command) throws IllegalArgumentException {
        // load developer and project infos
        Developer developer = loadDeveloperPort.loadDeveloper(
                new LoadDeveloperCommand(command.getDeveloperId())
        );

        List<Project> developerProjects = loadDeveloperProjectsPort.loadDeveloperProjects(
                new LoadDeveloperProjectsCommand(command.getDeveloperId())
        );

        developer.setProjectList(developerProjects);

        Project project = loadProjectPort.loadProject(
                new LoadProjectCommand(command.getProjectId())
        );

        // check if the dev is available
        boolean isAvailable = developer.checkAvailability(project);

        if (!isAvailable) {
            return new AssignDeveloperToTeamResponse.SingleErrorResponse("Developer is not available for this project.");
        }

        // add the dev to the team and save team
        Team team = loadTeamPort.loadTeam(
                new LoadTeamCommand(command.getProjectId())
        );

        List<String> addDeveloperErrors = project.addDeveloper(developer, false);

        if (addDeveloperErrors != null) {
            return new AssignDeveloperToTeamResponse.MultipleErrorsResponse(addDeveloperErrors);
        }

        saveTeamPort.saveTeam(new SaveTeamCommand(team));

        return new AssignDeveloperToTeamResponse.SuccessResponse(developer.toResponse());
    }
}
