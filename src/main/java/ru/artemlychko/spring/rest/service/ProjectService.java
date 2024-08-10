package ru.artemlychko.spring.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.artemlychko.spring.rest.dto.ProjectDTO;
import ru.artemlychko.spring.rest.entity.Project;
import ru.artemlychko.spring.rest.mapper.ProjectMapper;
import ru.artemlychko.spring.rest.repository.ProjectRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    public ProjectDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Project not found"));
        return projectMapper.toDto(project);
    }

    public List<ProjectDTO> getAllProjects() {
        List<ProjectDTO> projectDTOList = projectRepository.findAll().stream()
                .map(projectMapper::toDto)
                .collect(Collectors.toList());
        if (projectDTOList.isEmpty()) {
            throw new IllegalArgumentException("List is empty");
        }
        return projectDTOList;
    }

    public void createProject(ProjectDTO projectDTO) {
        Project project = projectMapper.toEntity(projectDTO);
        if (project.getName() == null) {
            throw new IllegalArgumentException("Project fields cannot be empty");
        }
        projectRepository.save(project);
    }

    public void updateProject(Long id, ProjectDTO projectDTO) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(
                String.format("Project with id '%s' not found", id))
        );
        if (project.getName() != null) {
            project.setName(projectDTO.getName());
        }
        projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        if (projectRepository.findById(id).isPresent()) {
            projectRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException(String.format("Project with id '%s' not found", id));
        }
    }
}