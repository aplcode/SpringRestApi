package ru.artemlychko.spring.rest.dto;

import jakarta.validation.constraints.NotNull;

public class DepartmentUpdateDto {
    @NotNull
    private Long id;
    @NotNull
    private String name;

    public DepartmentUpdateDto() {
    }

    public DepartmentUpdateDto(@NotNull Long id, @NotNull String name) {
        this.id = id;
        this.name = name;
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
}
