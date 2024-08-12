package ru.artemlychko.spring.rest.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.artemlychko.spring.rest.dto.DepartmentCreateDto;
import ru.artemlychko.spring.rest.dto.DepartmentResponseDto;
import ru.artemlychko.spring.rest.dto.DepartmentUpdateDto;
import ru.artemlychko.spring.rest.service.DepartmentService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping(value = "/departments/{id}")
    public DepartmentResponseDto getDepartmentById(@PathVariable("id") Long id) {
           return departmentService.getDepartmentById(id);
    }

    @GetMapping(value = "/departments/all")
    public List<DepartmentResponseDto> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @PostMapping(value = "/departments")
    public String createDepartment(@RequestBody DepartmentCreateDto departmentCreateDto) {
        departmentService.createDepartment(departmentCreateDto);
        return "Department with name " + departmentCreateDto.getName() + " was created";
    }

    @PutMapping(value = "/departments")
    public String updateDepartment(@RequestBody DepartmentUpdateDto departmentUpdateDto) {
        departmentService.updateDepartment(departmentUpdateDto);
        return "Department with id " + departmentUpdateDto.getId() + " was updated";
    }

    @DeleteMapping("/departments/{id}")
    public String deleteDepartment(@PathVariable("id") Long id) {
        departmentService.deleteDepartment(id);
        return "Department with id " + id + " was deleted";
    }
}
