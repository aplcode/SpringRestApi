package ru.artemlychko.spring.rest.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import ru.artemlychko.spring.rest.entity.Employee;

import java.util.List;

public class DepartmentResponseDto {
    @NotNull
    private Long id;

    @NotNull
    private String name;

    private List<EmployeeUpdateDto> employeeList;

    public DepartmentResponseDto() {
    }

    public DepartmentResponseDto(Long id, String name, List<EmployeeUpdateDto> employeeList) {
        this.id = id;
        this.name = name;
        this.employeeList = employeeList;
    }

    public @NotNull Long getId() {
        return id;
    }

    public void setId(@NotNull Long id) {
        this.id = id;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public List<EmployeeUpdateDto> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<EmployeeUpdateDto> employeeList) {
        this.employeeList = employeeList;
    }
}
