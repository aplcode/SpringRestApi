package ru.artemlychko.spring.rest.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.artemlychko.spring.rest.dto.DepartmentDTO;
import ru.artemlychko.spring.rest.entity.Department;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    DepartmentDTO toDto(Department department);

    Department toEntity(DepartmentDTO departmentDTO);
}
