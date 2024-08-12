package ru.artemlychko.spring.rest.dto;

public class EmployeeCreateDto {
    private String firstName;
    private String lastName;

    private Long department;

    public EmployeeCreateDto() {
    }

    public EmployeeCreateDto(String firstName, String lastName, Long department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
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
