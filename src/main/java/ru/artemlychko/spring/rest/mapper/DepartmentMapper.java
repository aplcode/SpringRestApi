package ru.artemlychko.spring.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.artemlychko.spring.rest.dto.DepartmentCreateDto;
import ru.artemlychko.spring.rest.dto.DepartmentResponseDto;
import ru.artemlychko.spring.rest.dto.DepartmentUpdateDto;
import ru.artemlychko.spring.rest.entity.Department;

@Mapper(uses = EmployeeMapper.class, componentModel = "spring")
public interface DepartmentMapper {

    Department toDepartment(DepartmentCreateDto dto);

    @Mapping(source = "employeeList", target = "employeeList")
    DepartmentResponseDto toDepartmentResponseDto(Department department);
}
