package ru.artemlychko.spring.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.artemlychko.spring.rest.dto.EmployeeDTO;
import ru.artemlychko.spring.rest.entity.Employee;

@Mapper(componentModel = "spring")
@Component
public interface EmployeeMapper {
    @Mapping(source = "department.id", target = "departmentId")
    EmployeeDTO toDto(Employee employee);

    @Mapping(source = "departmentId", target = "department.id")
    Employee toEntity(EmployeeDTO employeeDTO);

}
