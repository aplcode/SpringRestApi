package ru.artemlychko.spring.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.artemlychko.spring.rest.dto.*;
import ru.artemlychko.spring.rest.entity.Employee;
import ru.artemlychko.spring.rest.entity.Project;

import java.util.List;

@Mapper(uses = EmployeeMapper.class, componentModel = "spring")
public interface ProjectMapper {
    Project toProject(ProjectCreateDto dto);

    ProjectUpdateDto toProjectUpdateDto(Project project);

    @Mapping(source = "employeeList", target = "employeeList")
    ProjectResponseDto toProjectResponseDto(Project project);
}
