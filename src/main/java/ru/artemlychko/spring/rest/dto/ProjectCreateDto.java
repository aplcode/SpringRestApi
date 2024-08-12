package ru.artemlychko.spring.rest.dto;

import ru.artemlychko.spring.rest.entity.Employee;

import java.util.List;

public class ProjectCreateDto {
    private String name;

    public ProjectCreateDto() {
    }

    public ProjectCreateDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

