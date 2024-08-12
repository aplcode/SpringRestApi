package ru.artemlychko.spring.rest.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.artemlychko.spring.rest.dto.DepartmentCreateDto;
import ru.artemlychko.spring.rest.dto.DepartmentResponseDto;
import ru.artemlychko.spring.rest.dto.DepartmentUpdateDto;
import ru.artemlychko.spring.rest.exceptions.NoSuchElementException;
import ru.artemlychko.spring.rest.service.DepartmentService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class DepartmentControllerTest {
    @InjectMocks
    private DepartmentController departmentController;

    @Mock
    private DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDepartmentById() {
        DepartmentResponseDto departmentResponseDto = new DepartmentResponseDto(1L, "Name", null);

        when(departmentService.getDepartmentById(1L)).thenReturn(departmentResponseDto);

        DepartmentResponseDto department = departmentController.getDepartmentById(1L);
        Assertions.assertNotNull(department);
        Assertions.assertEquals(department.getId(), departmentResponseDto.getId());
        Assertions.assertEquals(department.getName(), departmentResponseDto.getName());
    }

    @Test
    void testGetDepartmentByIdNotFound() {
        when(departmentService.getDepartmentById(1L)).thenThrow(new NoSuchElementException("Department not found"));

        Assertions.assertThrows(NoSuchElementException.class, () -> departmentController.getDepartmentById(1L));
    }

    @Test
    void testGetAllDepartments(){
        List<DepartmentResponseDto> departments = new ArrayList<>();
        departments.add(new DepartmentResponseDto(1L, "Department 1", null));
        departments.add(new DepartmentResponseDto(2L, "Department 2", null));

        when(departmentService.getAllDepartments()).thenReturn(departments);

        List<DepartmentResponseDto> responseDtoList = departmentService.getAllDepartments();
        Assertions.assertNotNull(responseDtoList);
        Assertions.assertEquals(2, responseDtoList.size());
    }

    @Test
    void testCreateDepartment() {
        DepartmentCreateDto departmentCreateDto = new DepartmentCreateDto("Department 1");

        doNothing().when(departmentService).createDepartment(departmentCreateDto);

        String result = departmentController.createDepartment(departmentCreateDto);
        Assertions.assertEquals(result, "Department with name " + departmentCreateDto.getName() + " was created");
    }

    @Test
    void testCreateEmptyDepartment() {
        DepartmentCreateDto departmentCreateDto = new DepartmentCreateDto();

        doThrow(new NoSuchElementException("Department fields cannot be empty")).when(departmentService).createDepartment(departmentCreateDto);

        Assertions.assertThrows(NoSuchElementException.class, () -> departmentController.createDepartment(departmentCreateDto));
    }

    @Test
    void testUpdateDepartment() {
        DepartmentUpdateDto departmentUpdateDto = new DepartmentUpdateDto(1L, "Updated Department Name");

        doNothing().when(departmentService).updateDepartment(departmentUpdateDto);

        String result = departmentController.updateDepartment(departmentUpdateDto);
        Assertions.assertEquals(result, "Department with id " + departmentUpdateDto.getId() + " was updated");
    }

    @Test
    void testUpdateDepartmentNotFound() {
        DepartmentUpdateDto departmentUpdateDto = new DepartmentUpdateDto(1L, "Updated Department Name");

        doThrow(new NoSuchElementException("Department not found")).when(departmentService).updateDepartment(departmentUpdateDto);

        Assertions.assertThrows(NoSuchElementException.class, () -> departmentController.updateDepartment(departmentUpdateDto));
    }

    @Test
    void testDeleteDepartment() {
        doNothing().when(departmentService).deleteDepartment(1L);

        String result = departmentController.deleteDepartment(1L);
        Assertions.assertEquals(result, "Department with id " + 1L + " was deleted");
    }

    @Test
    void testDeleteDepartmentNotFound() {
        doThrow(new NoSuchElementException("Department not found")).when(departmentService).deleteDepartment(1L);

        Assertions.assertThrows(NoSuchElementException.class, () -> departmentController.deleteDepartment(1L));
    }
}
