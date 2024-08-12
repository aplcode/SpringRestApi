package ru.artemlychko.spring.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.artemlychko.spring.rest.dto.EmployeeCreateDto;
import ru.artemlychko.spring.rest.dto.EmployeeResponseDto;
import ru.artemlychko.spring.rest.dto.EmployeeUpdateDto;
import ru.artemlychko.spring.rest.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/employees/{id}")
    public EmployeeResponseDto getEmployeeById(@PathVariable("id") Long id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping(value = "/employees/all")
    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping("/employees")
    public String createEmployee(@RequestBody EmployeeCreateDto employeeCreateDto) {
        employeeService.createEmployee(employeeCreateDto);
        return "Employee " + employeeCreateDto.getLastName() + " " +
                employeeCreateDto.getFirstName() +
                "in department with id" + employeeCreateDto.getDepartment() +
                " was created";
    }

    @PutMapping("/employees")
    public String updateEmployee(@RequestBody EmployeeUpdateDto employeeUpdateDto) {
        employeeService.updateEmployee(employeeUpdateDto);
        return "Employee with id " + employeeUpdateDto.getId() + " was updated";
    }

    @DeleteMapping("/employees/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return "Employee with id " + id + " was deleted";
    }
}
