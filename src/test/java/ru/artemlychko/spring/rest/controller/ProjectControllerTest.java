package ru.artemlychko.spring.rest.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.artemlychko.spring.rest.dto.*;
import ru.artemlychko.spring.rest.exceptions.NoSuchElementException;
import ru.artemlychko.spring.rest.service.DepartmentService;
import ru.artemlychko.spring.rest.service.ProjectService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class ProjectControllerTest {
    @InjectMocks
    private ProjectController projectController;

    @Mock
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProjectById() {
        ProjectResponseDto projectResponseDto = new ProjectResponseDto(1L, "Name", null);

        when(projectService.getProjectById(1L)).thenReturn(projectResponseDto);

        ProjectResponseDto project = projectController.getProjectById(1L);
        Assertions.assertNotNull(project);
        Assertions.assertEquals(project.getId(), projectResponseDto.getId());
        Assertions.assertEquals(project.getName(), projectResponseDto.getName());
    }

    @Test
    void testGetProjectByIdNotFound() {
        when(projectService.getProjectById(1L)).thenThrow(new NoSuchElementException("Project not found"));

        Assertions.assertThrows(NoSuchElementException.class, () -> projectController.getProjectById(1L));
    }

    @Test
    void testGetAllProjects(){
        List<ProjectResponseDto> projects = new ArrayList<>();
        projects.add(new ProjectResponseDto(1L, "Project 1", null));
        projects.add(new ProjectResponseDto(2L, "Project 2", null));

        when(projectService.getAllProjects()).thenReturn(projects);

        List<ProjectResponseDto> responseDtoList = projectService.getAllProjects();
        Assertions.assertNotNull(responseDtoList);
        Assertions.assertEquals(2, responseDtoList.size());
    }

    @Test
    void testCreateProject() {
        ProjectCreateDto projectCreateDto = new ProjectCreateDto("Project1");

        doNothing().when(projectService).createProject(projectCreateDto);

        String result = projectController.createProject(projectCreateDto);
        Assertions.assertEquals(result, "Project with name " + projectCreateDto.getName() + " was created");
    }

    @Test
    void testCreateEmptyProject() {
        ProjectCreateDto projectCreateDto = new ProjectCreateDto();

        doThrow(new NoSuchElementException("Project fields cannot be empty")).when(projectService).createProject(projectCreateDto);

        Assertions.assertThrows(NoSuchElementException.class, () -> projectController.createProject(projectCreateDto));
    }

    @Test
    void testUpdateProject() {
        ProjectUpdateDto projectUpdateDto = new ProjectUpdateDto(1L, "Updated Project Name");

        doNothing().when(projectService).updateProject(projectUpdateDto);

        String result = projectController.updateProject(projectUpdateDto);
        Assertions.assertEquals(result, "Project with id " + projectUpdateDto.getId() + " was updated");
    }

    @Test
    void testUpdateProjectNotFound() {
        ProjectUpdateDto projectUpdateDto = new ProjectUpdateDto(1L, "Updated Project Name");

        doThrow(new NoSuchElementException("Project not found")).when(projectService).updateProject(projectUpdateDto);

        Assertions.assertThrows(NoSuchElementException.class, () -> projectController.updateProject(projectUpdateDto));
    }

    @Test
    void testDeleteProject() {
        doNothing().when(projectService).deleteProject(1L);

        String result = projectController.deleteProject(1L);
        Assertions.assertEquals(result, "Project with id " + 1L + " was deleted");
    }

    @Test
    void testDeleteProjectNotFound() {
        doThrow(new NoSuchElementException("Project not found")).when(projectService).deleteProject(1L);

        Assertions.assertThrows(NoSuchElementException.class, () -> projectController.deleteProject(1L));
    }
}
