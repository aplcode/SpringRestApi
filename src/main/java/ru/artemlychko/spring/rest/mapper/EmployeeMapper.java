package ru.artemlychko.spring.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.artemlychko.spring.rest.dto.EmployeeCreateDto;
import ru.artemlychko.spring.rest.dto.EmployeeResponseDto;
import ru.artemlychko.spring.rest.dto.EmployeeUpdateDto;
import ru.artemlychko.spring.rest.entity.Employee;

import java.util.List;

@Mapper(uses = ProjectMapper.class, componentModel = "spring")
public interface EmployeeMapper {
    Employee toEmployee(EmployeeCreateDto dto);

    List<EmployeeUpdateDto> toEmployeeUpdateDto(List<Employee> employeeList);

    @Mapping(source = "projectList", target = "projectList")
    EmployeeResponseDto toEmployeeResponseDto(Employee employee);
}
