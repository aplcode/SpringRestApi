package ru.artemlychko.spring.rest.dto;

import ru.artemlychko.spring.rest.entity.Employee;

import java.util.List;

public class ProjectResponseDto {
    private Long id;

    private String name;

    private List<EmployeeUpdateDto> employeeList;

    public ProjectResponseDto() {
    }

    public ProjectResponseDto(Long id, String name, List<EmployeeUpdateDto> employeeList) {
        this.id = id;
        this.name = name;
        this.employeeList = employeeList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EmployeeUpdateDto> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<EmployeeUpdateDto> employeeList) {
        this.employeeList = employeeList;
    }
}
