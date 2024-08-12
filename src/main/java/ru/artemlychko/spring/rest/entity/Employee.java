package ru.artemlychko.spring.rest.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    @Column(name = "employee_firstname")
    private String firstName;

    @Column(name = "employee_lastname")
    private String lastName;

    @Column(name = "department")
    private Long department;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "employees_projects"
    , joinColumns = @JoinColumn(name = "employee_id")
    , inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<Project> projectList;

    public Employee() {

    }

    public Employee(Long id, String firstName, String lastName, Long department) {
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

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public Long getDepartment() {
        return department;
    }

    public void setDepartment(Long department) {
        this.department = department;
    }
}
