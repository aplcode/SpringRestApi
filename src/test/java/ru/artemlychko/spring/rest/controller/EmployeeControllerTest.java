package ru.artemlychko.spring.rest.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.artemlychko.spring.rest.dto.*;
import ru.artemlychko.spring.rest.exceptions.NoSuchElementException;
import ru.artemlychko.spring.rest.service.EmployeeService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class EmployeeControllerTest {
    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetEmployeeById() {
        EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto(1L, "First Name", "Last Name", 1L, null);

        when(employeeService.getEmployeeById(1L)).thenReturn(employeeResponseDto);

        EmployeeResponseDto result = employeeController.getEmployeeById(1L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), employeeResponseDto.getId());
        Assertions.assertEquals(result.getFirstName(), employeeResponseDto.getFirstName());
        Assertions.assertEquals(result.getLastName(), employeeResponseDto.getLastName());
        Assertions.assertEquals(result.getDepartmentId(), employeeResponseDto.getDepartmentId());
    }

    @Test
    void testGetEmployeeByIdNotFound() {
        when(employeeService.getEmployeeById(1L)).thenThrow(new NoSuchElementException("Employee not found"));

        Assertions.assertThrows(NoSuchElementException.class, () -> employeeController.getEmployeeById(1L));
    }

    @Test
    void testGetAllEmployees() {
        List<EmployeeResponseDto> employees = new ArrayList<>();
        employees.add(new EmployeeResponseDto(1L, "First Name 1", "Last Name 1", 1L, null));
        employees.add(new EmployeeResponseDto(2L, "First Name 2", "Last Name 2", 2L, null));

        when(employeeService.getAllEmployees()).thenReturn(employees);

        List<EmployeeResponseDto> responseDtoList = employeeService.getAllEmployees();
        Assertions.assertNotNull(responseDtoList);
        Assertions.assertEquals(2, responseDtoList.size());
    }

    @Test
    void testCreateEmployee() {
        EmployeeCreateDto employeeCreateDto = new EmployeeCreateDto("First Name", "Last Name", 1L);

        doNothing().when(employeeService).createEmployee(employeeCreateDto);

        String result = employeeController.createEmployee(employeeCreateDto);
        Assertions.assertEquals(result,
                "Employee " + employeeCreateDto.getLastName() + " " +
                        employeeCreateDto.getFirstName() +
                        "in department with id" + employeeCreateDto.getDepartment() +
                        " was created");
    }

    @Test
    void testCreateEmptyEmployee() {
        EmployeeCreateDto employeeCreateDto = new EmployeeCreateDto();

        doThrow(new NoSuchElementException("Employee fields cannot be empty")).when(employeeService).createEmployee(employeeCreateDto);

        Assertions.assertThrows(NoSuchElementException.class, () -> employeeController.createEmployee(employeeCreateDto));
    }

    @Test
    void testUpdateEmployee() {
        EmployeeUpdateDto employeeUpdateDto = new EmployeeUpdateDto(1L, "New Fist Name", "New Last Name", 2L);

        doNothing().when(employeeService).updateEmployee(employeeUpdateDto);

        String result = employeeController.updateEmployee(employeeUpdateDto);
        Assertions.assertEquals(result, "Employee with id " + employeeUpdateDto.getId() + " was updated");
    }

    @Test
    void testUpdateEmployeeNotFound() {
        EmployeeUpdateDto employeeUpdateDto = new EmployeeUpdateDto(1L, "New Fist Name", "New Last Name", 2L);

        doThrow(new NoSuchElementException("Employee not found")).when(employeeService).updateEmployee(employeeUpdateDto);

        Assertions.assertThrows(NoSuchElementException.class, () -> employeeController.updateEmployee(employeeUpdateDto));
    }

    @Test
    void testDeleteEmployee() {
        doNothing().when(employeeService).deleteEmployee(1L);

        String result = employeeController.deleteEmployee(1L);
        Assertions.assertEquals(result, "Employee with id " + 1L + " was deleted");
    }

    @Test
    void testDeleteEmployeeNotFound() {
        doThrow(new NoSuchElementException("Employee not found")).when(employeeService).deleteEmployee(1L);

        Assertions.assertThrows(NoSuchElementException.class, () -> employeeController.deleteEmployee(1L));
    }

}
