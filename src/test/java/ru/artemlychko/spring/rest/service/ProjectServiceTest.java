package ru.artemlychko.spring.rest.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.artemlychko.spring.rest.dto.ProjectCreateDto;
import ru.artemlychko.spring.rest.dto.ProjectResponseDto;
import ru.artemlychko.spring.rest.dto.ProjectUpdateDto;
import ru.artemlychko.spring.rest.entity.Project;
import ru.artemlychko.spring.rest.exceptions.NoSuchElementException;
import ru.artemlychko.spring.rest.mapper.ProjectMapper;
import ru.artemlychko.spring.rest.mapper.ProjectMapper;
import ru.artemlychko.spring.rest.repository.ProjectRepository;
import ru.artemlychko.spring.rest.repository.ProjectRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMapper projectMapper;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProjectById_ShouldReturnProject_WhenProjectExists() {
        Long projectId = 1L;
        Project project = new Project();
        project.setId(projectId);
        project.setName("ProjectName");
        ProjectResponseDto projectResponseDto = new ProjectResponseDto();
        projectResponseDto.setId(projectId);
        projectResponseDto.setName("Project Name");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(projectMapper.toProjectResponseDto(project)).thenReturn(projectResponseDto);

        ProjectResponseDto result = projectService.getProjectById(projectId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(projectId, result.getId());
        Assertions.assertEquals("Project Name", result.getName());
    }

    @Test
    void getProjectById_ShouldThrowException_WhenProjectDoesNotExist() {
        Long projectId = 1L;
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> projectService.getProjectById(projectId));
    }

    @Test
    void getAllProjects_ShouldReturnListOfProjects_WhenProjectsExist() {
        Project project1 = new Project(1L, "Project1");
        Project project2 = new Project(2L, "Project2");

        ProjectResponseDto projectResponseDto1 = new ProjectResponseDto(1L, "Project1", null);
        ProjectResponseDto projectResponseDto2 = new ProjectResponseDto(2L, "Project2", null);

        when(projectRepository.findAll()).thenReturn(Arrays.asList(project1, project2));
        when(projectMapper.toProjectResponseDto(project1)).thenReturn(projectResponseDto1);
        when(projectMapper.toProjectResponseDto(project2)).thenReturn(projectResponseDto2);

        List<ProjectResponseDto> result = projectService.getAllProjects();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void createProject_ShouldSaveProject_WhenProjectIsValid() {
        ProjectCreateDto projectCreateDto = new ProjectCreateDto("Project1");

        Project project = new Project(1L, "Project1");

        when(projectMapper.toProject(projectCreateDto)).thenReturn(project);

        projectService.createProject(projectCreateDto);

        verify(projectRepository, times(1)).save(project);
    }
    //
    @Test
    void createProject_ShouldThrowException_WhenProjectFieldsAreEmpty() {
        ProjectCreateDto projectCreateDto = new ProjectCreateDto();
        Project project = new Project();

        when(projectMapper.toProject(projectCreateDto)).thenReturn(project);
        assertThrows(IllegalArgumentException.class, () -> projectService.createProject(projectCreateDto));
    }

    @Test
    void testUpdateProject() {
        Long projectId = 1L;
        ProjectUpdateDto projectUpdateDto = new ProjectUpdateDto(projectId, "New Name");


        Project project = new Project(projectId, "Old name");


        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        projectService.updateProject(projectUpdateDto);

        Assertions.assertEquals("New Name", project.getName());
        verify(projectRepository).findById(projectId);
        verify(projectRepository).save(project);
    }

    @Test
    void testUpdateProject_NotFound() {
        long projectId = 1L;
        ProjectUpdateDto projectUpdateDto = new ProjectUpdateDto(projectId, "New Name");

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> projectService.updateProject(projectUpdateDto));
        verify(projectRepository).findById(projectId);
        verify(projectRepository, never()).save(any(Project.class));
    }
    @Test
    void updateProject_ShouldThrowException_WhenProjectDoesNotExist() {
        long projectId = 1L;
        ProjectUpdateDto projectUpdateDto = new ProjectUpdateDto();

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> projectService.updateProject(projectUpdateDto));
    }

    @Test
    void deleteProject_ShouldDeleteProject_WhenProjectExists() {
        Long projectId = 1L;
        Project project = new Project();
        project.setId(projectId);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        projectService.deleteProject(projectId);

        verify(projectRepository, times(1)).deleteById(projectId);
    }

    @Test
    void deleteProject_ShouldThrowException_WhenProjectDoesNotExist() {
        Long projectId = 1L;
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> projectService.deleteProject(projectId));
    }
}
