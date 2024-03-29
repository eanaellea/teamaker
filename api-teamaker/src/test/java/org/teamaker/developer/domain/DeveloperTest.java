package org.teamaker.developer.domain;

import org.junit.jupiter.api.Test;
import org.teamaker.project.domain.Project;
import org.teamaker.project.domain.ProjectPriority;
import org.teamaker.project.domain.ProjectStatus;
import org.teamaker.team.domain.Team;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DeveloperTest {
    private static List<Project> getMockProjects() {
        List<Project> projectList = new ArrayList<>();
        Project project1 = new Project("proj1", "Project 1", "Description 1", ProjectPriority.NORMAL, ProjectStatus.PENDING,
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 10),
                new Team("projectId", new ArrayList<>(), false), Map.of()
        );
        Project project2 = new Project("proj2", "Project 2", "Description 2", ProjectPriority.NORMAL, ProjectStatus.PENDING,
                LocalDate.of(2023, 2, 1),
                LocalDate.of(2023, 2, 15),
                new Team("projectId", new ArrayList<>(), false), Map.of()
        );

        projectList.add(project1);
        projectList.add(project2);
        return projectList;
    }

    @Test
    public void testCheckAvailability_True() {
        Developer developer = new Developer("dev123", "John Doe", "johndoe@example.com", LocalDate.of(2023, 1, 1), null);

        List<Project> projectList = getMockProjects();

        developer.setProjectList(projectList);

        Project projectToCheck = new Project("proj3", "Project 3", "Description 3", ProjectPriority.NORMAL, ProjectStatus.PENDING,
                LocalDate.of(2023, 1, 11),
                LocalDate.of(2023, 1, 20),
                new Team("projectId", new ArrayList<>(), false), Map.of()
        );

        boolean isAvailable = developer.checkAvailability(projectToCheck);

        assertTrue(isAvailable);
    }

    @Test
    public void testCheckAvailability_False() {
        Developer developer = new Developer("dev123", "John Doe", "johndoe@example.com", LocalDate.of(2023, 1, 1), null);

        List<Project> projectList = getMockProjects();

        developer.setProjectList(projectList);

        Project projectToCheck = new Project("proj3", "Project 3", "Description 3", ProjectPriority.NORMAL, ProjectStatus.PENDING,
                LocalDate.of(2023, 1, 5),
                LocalDate.of(2023, 1, 20),
                new Team("projectId", new ArrayList<>(), false), Map.of()
        );

        boolean isAvailable = developer.checkAvailability(projectToCheck);

        assertFalse(isAvailable);
    }

    @Test
    public void testCheckAvailability_NoProjects() {
        Developer developer = new Developer("dev123", "John Doe", "johndoe@example.com", LocalDate.of(2023, 1, 1), null);

        Project projectToCheck = new Project("proj3", "Project 3", "Description 3", ProjectPriority.NORMAL, ProjectStatus.PENDING,
                LocalDate.of(2023, 1, 5),
                LocalDate.of(2023, 1, 20),
                new Team("projectId", new ArrayList<>(), false), Map.of()
        );

        assertThrows(IllegalStateException.class,
                () -> developer.checkAvailability(projectToCheck));
    }

    @Test
    public void testAddProject_Success() {
        Developer developer = new Developer("dev123", "John Doe", "johndoe@example.com", LocalDate.of(2023, 1, 1), null);
        List<Project> projectList = new ArrayList<>();
        developer.setProjectList(projectList);
        Project addedProject = new Project("proj1", "Project 1", "Description 1", ProjectPriority.NORMAL, ProjectStatus.PENDING, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 10), new Team("projectId", new ArrayList<>(), false), Map.of());
        developer.addProject(addedProject);
        assertTrue(developer.getProjectList().contains(addedProject));
    }

    @Test()
    public void testAddProject_Failure() {
        Developer developer = new Developer("dev123", "John Doe", "johndoe@example.com", LocalDate.of(2023, 1, 1), null);
        Project addedProject = new Project("proj1", "Project 1", "Description 1", ProjectPriority.NORMAL, ProjectStatus.PENDING, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 10), new Team("projectId", new ArrayList<>(), false), Map.of());
        assertThrows(IllegalStateException.class,
                () -> developer.addProject(addedProject));
    }

    @Test
    public void testGetExperienceLevel_Junior() {
        LocalDate hiringDate = LocalDate.now().minusYears(2); // Hiring date 2 years ago
        Developer developer = new Developer("123", "John Doe", "john@example.com", hiringDate, null);
        assertEquals(ExperienceLevel.JUNIOR, developer.getExperienceLevel());
    }

    @Test
    public void testGetExperienceLevel_Experienced() {
        LocalDate hiringDate = LocalDate.now().minusYears(4); // Hiring date 4 years ago
        Developer developer = new Developer("456", "Jane Smith", "jane@example.com", hiringDate, null);
        assertEquals(ExperienceLevel.EXPERIENCED, developer.getExperienceLevel());
    }

    @Test
    public void testGetExperienceLevel_Expert() {
        LocalDate hiringDate = LocalDate.now().minusYears(6); // Hiring date 6 years ago
        Developer developer = new Developer("789", "Bob Johnson", "bob@example.com", hiringDate, null);
        assertEquals(ExperienceLevel.EXPERT, developer.getExperienceLevel());
    }

    @Test
    public void testUpdateDeveloperInfo_AllFields() {
        String newName = "newName";
        String newEmail = "newEmail";
        Developer developer = new Developer("developerId", "John Doe", "john@doe.com", LocalDate.now(), null);
        developer.updateInfo(newName, newEmail);
        assertEquals(newName, developer.getFullName());
        assertEquals(newEmail, developer.getEmail());
    }

    @Test
    public void testUpdateDeveloperInfo_NullName() {
        String newEmail = "newEmail";
        Developer developer = new Developer("developerId", "John Doe", "john@doe.com", LocalDate.now(), null);
        developer.updateInfo(null, newEmail);
        assertEquals("John Doe", developer.getFullName());
        assertEquals(newEmail, developer.getEmail());
    }

    @Test
    public void testUpdateDeveloperInfo_NullEmail() {
        String newName = "newName";
        Developer developer = new Developer("developerId", "John Doe", "john@doe.com", LocalDate.now(), null);
        developer.updateInfo(newName, null);
        assertEquals(newName, developer.getFullName());
        assertEquals("john@doe.com", developer.getEmail());
    }

    @Test
    public void testResign() {
        Developer developer = new Developer("developerId", "John Doe", "john@doe.com", LocalDate.now(), null);
        developer.resign(List.of());
        assertEquals(LocalDate.now(), developer.getResignationDate());
    }
}