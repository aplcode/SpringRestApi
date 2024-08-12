package ru.artemlychko.spring.rest.dto;

import ru.artemlychko.spring.rest.entity.Employee;

import java.util.List;

public class ProjectUpdateDto {

    private Long id;

    private String name;


    public ProjectUpdateDto() {
    }

    public ProjectUpdateDto(Long id, String name) {
        this.id = id;
        this.name = name;
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
}