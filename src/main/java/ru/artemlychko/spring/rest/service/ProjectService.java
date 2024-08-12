package ru.artemlychko.spring.rest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.artemlychko.spring.rest.dto.ProjectCreateDto;
import ru.artemlychko.spring.rest.dto.ProjectResponseDto;
import ru.artemlychko.spring.rest.dto.ProjectUpdateDto;
import ru.artemlychko.spring.rest.entity.Project;
import ru.artemlychko.spring.rest.exceptions.NoSuchElementException;
import ru.artemlychko.spring.rest.mapper.ProjectMapper;
import ru.artemlychko.spring.rest.repository.ProjectRepository;

import java.util.List;

@Service
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    public ProjectResponseDto getProjectById(Long id) {
        return projectMapper.toProjectResponseDto(projectRepository.findById(id).orElseThrow(()->
                new NoSuchElementException("Project with id " + id + " not found")));
    }

    public List<ProjectResponseDto> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(projectMapper::toProjectResponseDto)
                .toList();
    }

    public void createProject(ProjectCreateDto projectCreateDto) {
        Project project = projectMapper.toProject(projectCreateDto);
        if (project.getName() == null) {
            throw new IllegalArgumentException("Project fields cannot be empty");
        }
        projectRepository.save(project);
    }

    public void updateProject(ProjectUpdateDto projectUpdateDto) {
        Project project = projectRepository.findById(projectUpdateDto.getId()).orElseThrow(() -> new NoSuchElementException(
                String.format("Project with id '%s' not found", projectUpdateDto.getId()))
        );
        if (projectUpdateDto.getName() != null) {
            project.setName(projectUpdateDto.getName());
        }
        projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        if (projectRepository.findById(id).isPresent()) {
            projectRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Project with id " + id + " not found");
        }
    }
}