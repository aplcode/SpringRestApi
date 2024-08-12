package ru.artemlychko.spring.rest.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.artemlychko.spring.rest.dto.DepartmentCreateDto;
import ru.artemlychko.spring.rest.dto.DepartmentResponseDto;
import ru.artemlychko.spring.rest.dto.DepartmentUpdateDto;
import ru.artemlychko.spring.rest.entity.Department;
import ru.artemlychko.spring.rest.exceptions.NoSuchElementException;
import ru.artemlychko.spring.rest.mapper.DepartmentMapper;
import ru.artemlychko.spring.rest.repository.DepartmentRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

class DepartmentServiceTest {
    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DepartmentMapper departmentMapper;

    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDepartmentById_ShouldReturnDepartment_WhenDepartmentExists() {
        Long departmentId = 1L;
        Department department = new Department();
        department.setId(departmentId);
        department.setName("DepartmentName");
        DepartmentResponseDto departmentResponseDto = new DepartmentResponseDto();
        departmentResponseDto.setId(departmentId);
        departmentResponseDto.setName("Department Name");

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        when(departmentMapper.toDepartmentResponseDto(department)).thenReturn(departmentResponseDto);

        DepartmentResponseDto result = departmentService.getDepartmentById(departmentId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(departmentId, result.getId());
        Assertions.assertEquals("Department Name", result.getName());
    }

    @Test
    void getDepartmentById_ShouldThrowException_WhenDepartmentDoesNotExist() {
        Long departmentId = 1L;
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> departmentService.getDepartmentById(departmentId));
    }

    @Test
    void getAllDepartments_ShouldReturnListOfDepartments_WhenDepartmentsExist() {
        Department department1 = new Department(1L, "Department1");
        Department department2 = new Department(2L, "Department2");

        DepartmentResponseDto departmentResponseDto1 = new DepartmentResponseDto(1L, "Department1", null);
        DepartmentResponseDto departmentResponseDto2 = new DepartmentResponseDto(2L, "Department2", null);

        when(departmentRepository.findAll()).thenReturn(Arrays.asList(department1, department2));
        when(departmentMapper.toDepartmentResponseDto(department1)).thenReturn(departmentResponseDto1);
        when(departmentMapper.toDepartmentResponseDto(department2)).thenReturn(departmentResponseDto2);

        List<DepartmentResponseDto> result = departmentService.getAllDepartments();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void createDepartment_ShouldSaveDepartment_WhenDepartmentIsValid() {
        DepartmentCreateDto departmentCreateDto = new DepartmentCreateDto("Department1");

        Department department = new Department(1L, "Department1");

        when(departmentMapper.toDepartment(departmentCreateDto)).thenReturn(department);

        departmentService.createDepartment(departmentCreateDto);

        verify(departmentRepository, times(1)).save(department);
    }
//
    @Test
    void createDepartment_ShouldThrowException_WhenDepartmentFieldsAreEmpty() {
        DepartmentCreateDto departmentCreateDto = new DepartmentCreateDto();
        Department department = new Department();

        when(departmentMapper.toDepartment(departmentCreateDto)).thenReturn(department);
        assertThrows(IllegalArgumentException.class, () -> departmentService.createDepartment(departmentCreateDto));
    }

    @Test
    void testUpdateDepartment() {
        Long departmentId = 1L;
        DepartmentUpdateDto departmentUpdateDto = new DepartmentUpdateDto(departmentId, "New Name");


        Department department = new Department(departmentId, "Old name");


        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));

        departmentService.updateDepartment(departmentUpdateDto);

        Assertions.assertEquals("New Name", department.getName());
        verify(departmentRepository).findById(departmentId);
        verify(departmentRepository).save(department);
    }

    @Test
    void testUpdateDepartment_NotFound() {
        long departmentId = 1L;
        DepartmentUpdateDto departmentUpdateDto = new DepartmentUpdateDto(departmentId, "New Name");

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> departmentService.updateDepartment(departmentUpdateDto));
        verify(departmentRepository).findById(departmentId);
        verify(departmentRepository, never()).save(any(Department.class));
    }
    @Test
    void updateDepartment_ShouldThrowException_WhenDepartmentDoesNotExist() {
        long departmentId = 1L;
        DepartmentUpdateDto departmentUpdateDto = new DepartmentUpdateDto();

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> departmentService.updateDepartment(departmentUpdateDto));
    }

    @Test
    void deleteDepartment_ShouldDeleteDepartment_WhenDepartmentExists() {
        Long departmentId = 1L;
        Department department = new Department();
        department.setId(departmentId);

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));

        departmentService.deleteDepartment(departmentId);

        verify(departmentRepository, times(1)).deleteById(departmentId);
    }

    @Test
    void deleteDepartment_ShouldThrowException_WhenDepartmentDoesNotExist() {
        Long departmentId = 1L;
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> departmentService.deleteDepartment(departmentId));
    }
}
