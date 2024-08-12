package ru.artemlychko.spring.rest.dto;

public class EmployeeUpdateDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Long department;

    public EmployeeUpdateDto() {
    }

    public EmployeeUpdateDto(Long id, String firstName, String lastName, Long department) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
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

    public Long getDepartment() {
        return department;
    }

    public void setDepartment(Long department) {
        this.department = department;
    }
}
