package ru.artemlychko.spring.rest.dto;

import ru.artemlychko.spring.rest.entity.Project;

import java.util.List;

public class EmployeeDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private Long departmentId;

    private List<Project> projectList;

    public EmployeeDTO() {

    }

    public EmployeeDTO(Long id, String firstName, String lastName, Long departmentId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.departmentId = departmentId;
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

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}
