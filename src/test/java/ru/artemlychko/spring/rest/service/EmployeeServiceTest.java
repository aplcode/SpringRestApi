package ru.artemlychko.spring.rest.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.artemlychko.spring.rest.dto.*;
import ru.artemlychko.spring.rest.entity.Department;
import ru.artemlychko.spring.rest.entity.Employee;
import ru.artemlychko.spring.rest.exceptions.NoSuchElementException;
import ru.artemlychko.spring.rest.mapper.DepartmentMapper;
import ru.artemlychko.spring.rest.mapper.EmployeeMapper;
import ru.artemlychko.spring.rest.repository.DepartmentRepository;
import ru.artemlychko.spring.rest.repository.EmployeeRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getEmployeeById_ShouldReturnDepartment_WhenUserExists() {
        Long employeeId = 1L;
        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setFirstName("First Name");
        employee.setLastName("Last Name");
        employee.setDepartment(1L);
        EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();
        employeeResponseDto.setId(employeeId);
        employeeResponseDto.setFirstName("First Name");
        employeeResponseDto.setLastName("Last Name");
        employeeResponseDto.setDepartment(1L);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeMapper.toEmployeeResponseDto(employee)).thenReturn(employeeResponseDto);

        EmployeeResponseDto result = employeeService.getEmployeeById(employeeId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(employeeId, result.getId());
        Assertions.assertEquals("First Name", result.getFirstName());
        Assertions.assertEquals("Last Name", result.getLastName());
        Assertions.assertEquals(1L, result.getDepartmentId());
    }

    @Test
    void getEmployeeById_ShouldThrowException_WhenEmployeeDoesNotExist() {
        Long employeeId = 1L;
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> employeeService.getEmployeeById(employeeId));
    }

    @Test
    void getAllEmployees_ShouldReturnListOfEmployees_WhenEmployeesExist() {
        Employee employee1 = new Employee(1L, "First Name 1", "Last Name 1", 1L);
        Employee employee2 = new Employee(2L, "First Name 2", "Last Name 2", 2L);

        EmployeeResponseDto employeeResponseDto1 = new EmployeeResponseDto(1L, "Fist Name 1", "Last Name 1", 1L, null);
        EmployeeResponseDto employeeResponseDto2 = new EmployeeResponseDto(2L, "First Name 2", "Last Name 2", 2L, null);

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));
        when(employeeMapper.toEmployeeResponseDto(employee1)).thenReturn(employeeResponseDto1);
        when(employeeMapper.toEmployeeResponseDto(employee2)).thenReturn(employeeResponseDto2);

        List<EmployeeResponseDto> result = employeeService.getAllEmployees();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void createEmployee_ShouldSaveEmployee_WhenEmployeeIsValid() {
        EmployeeCreateDto employeeCreateDto = new EmployeeCreateDto("Fist Name", "Last Name", 1L);

        Employee employee = new Employee(1L, "Fist Name", "Last Name", 1L);

        when(employeeMapper.toEmployee(employeeCreateDto)).thenReturn(employee);

        employeeService.createEmployee(employeeCreateDto);

        verify(employeeRepository, times(1)).save(employee);
    }
    //
    @Test
    void createEmployee_ShouldThrowException_WhenEmployeeFieldsAreEmpty() {
        EmployeeCreateDto employeeCreateDto = new EmployeeCreateDto();
        Employee employee = new Employee();

        when(employeeMapper.toEmployee(employeeCreateDto)).thenReturn(employee);
        assertThrows(IllegalArgumentException.class, () -> employeeService.createEmployee(employeeCreateDto));
    }

    @Test
    void testUpdateEmployee() {
        Long employeeId = 1L;
        EmployeeUpdateDto employeeUpdateDto = new EmployeeUpdateDto(employeeId, "New First Name", "New Last Name", 2L);


        Employee employee = new Employee(employeeId, "Old First Name", "Old Last Name", 1L);


        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        employeeService.updateEmployee(employeeUpdateDto);

        Assertions.assertEquals("New First Name", employee.getFirstName());
        Assertions.assertEquals("New Last Name", employee.getLastName());
        Assertions.assertEquals(2L, employee.getDepartment());
        verify(employeeRepository).findById(employeeId);
        verify(employeeRepository).save(employee);
    }

    @Test
    void testUpdateEmployee_NotFound() {
        long employeeId = 1L;
        EmployeeUpdateDto employeeUpdateDto = new EmployeeUpdateDto(employeeId, "New First Name", "New Last Name", 1L);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> employeeService.updateEmployee(employeeUpdateDto));
        verify(employeeRepository).findById(employeeId);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void updateEmployee_ShouldThrowException_WhenEmployeeDoesNotExist() {
        long employeeId = 1L;
        EmployeeUpdateDto employeeUpdateDto = new EmployeeUpdateDto();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> employeeService.updateEmployee(employeeUpdateDto));
    }

    @Test
    void updateEmployeeWithEmptyDto(){
        long employeeId = 1L;
        EmployeeUpdateDto employeeUpdateDto = new EmployeeUpdateDto();

        Employee employee = new Employee(employeeId, "Old First Name", "Old Last Name", 1L);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        assertThrows(NoSuchElementException.class, () -> employeeService.updateEmployee(employeeUpdateDto));

    }

    @Test
    void deleteEmployee_ShouldDeleteEmployee_WhenEmployeeExists() {
        Long employeeId = 1L;
        Employee employee = new Employee();
        employee.setId(employeeId);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        employeeService.deleteEmployee(employeeId);

        verify(employeeRepository, times(1)).deleteById(employeeId);
    }

    @Test
    void deleteEmployee_ShouldThrowException_WhenEmployeeDoesNotExist() {
        Long employeeId = 1L;
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> employeeService.deleteEmployee(employeeId));
    }
}