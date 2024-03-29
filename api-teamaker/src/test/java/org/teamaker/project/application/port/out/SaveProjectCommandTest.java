package org.teamaker.project.application.port.out;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.teamaker.project.application.port.out.saveProject.SaveProjectCommand;
import org.teamaker.project.domain.Project;
import org.teamaker.project.domain.ProjectPriority;
import org.teamaker.project.domain.ProjectStatus;
import org.teamaker.team.domain.Team;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class SaveProjectCommandTest {
    private static Project project;

    @BeforeAll
    public static void setUp() {
        project = new Project("validProjectId", "validProjectName", "validProjectDescription", ProjectPriority.CRITICAL, ProjectStatus.ACCEPTED, LocalDate.now(), LocalDate.now().plusDays(1), new Team("projectId", new ArrayList<>(), false), Map.of());
    }

    @Test
    public void testConstructorValidData() {
        SaveProjectCommand command = new SaveProjectCommand(project);
        assertEquals(project, command.getProject());
    }

    @Test
    public void testConstructorEmptyProject() {
        assertThrows(ConstraintViolationException.class,
                () -> new SaveProjectCommand(null));
    }
}
