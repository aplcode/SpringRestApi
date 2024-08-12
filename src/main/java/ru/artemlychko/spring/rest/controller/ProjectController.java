package ru.artemlychko.spring.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.artemlychko.spring.rest.dto.ProjectCreateDto;
import ru.artemlychko.spring.rest.dto.ProjectResponseDto;
import ru.artemlychko.spring.rest.dto.ProjectUpdateDto;
import ru.artemlychko.spring.rest.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping(value = "/projects/all")
    public List<ProjectResponseDto> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping(value = "/projects/{id}")
    public ProjectResponseDto getProjectById(@PathVariable("id") Long id) {
        return projectService.getProjectById(id);
    }

    @PostMapping("/projects")
    public String createProject(@RequestBody ProjectCreateDto projectCreateDto) {
        projectService.createProject(projectCreateDto);
        return "Project with name " + projectCreateDto.getName() + " was created";
    }

    @PutMapping(value = "/projects")
    public String updateProject(@RequestBody ProjectUpdateDto projectUpdateDto) {
        projectService.updateProject(projectUpdateDto);
        return "Project with id " + projectUpdateDto.getId() + " was updated";
    }

    @DeleteMapping(value = "/projects/{id}")
    public String deleteProject(@PathVariable("id") long id) {
        projectService.deleteProject(id);
        return "Project with id " + id + " was deleted";
    }
}
