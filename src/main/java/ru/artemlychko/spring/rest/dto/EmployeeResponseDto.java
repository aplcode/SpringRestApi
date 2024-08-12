package ru.artemlychko.spring.rest.dto;

import ru.artemlychko.spring.rest.entity.Project;

import java.util.List;

public class EmployeeResponseDto {
    private Long id;

    private String firstName;

    private String lastName;

    private Long department;

    private List<ProjectUpdateDto> projectList;

    public EmployeeResponseDto() {

    }

    public EmployeeResponseDto(Long id, String firstName, String lastName, Long department, List<ProjectUpdateDto> projectList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.projectList = projectList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<ProjectUpdateDto> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectUpdateDto> projectList) {
        this.projectList = projectList;
    }

    public Long getDepartmentId() {
        return department;
    }

    public void setDepartment(Long department) {
        this.department = department;
    }
}
