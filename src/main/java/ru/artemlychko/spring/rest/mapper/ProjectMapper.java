package ru.artemlychko.spring.rest.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.artemlychko.spring.rest.dto.ProjectDTO;
import ru.artemlychko.spring.rest.entity.Project;

@Mapper(componentModel = "spring")
@Component
public interface ProjectMapper {
    ProjectDTO toDto(Project project);

    Project toEntity(ProjectDTO projectDTO);
}
