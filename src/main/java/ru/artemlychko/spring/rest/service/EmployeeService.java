package ru.artemlychko.spring.rest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.artemlychko.spring.rest.dto.EmployeeCreateDto;
import ru.artemlychko.spring.rest.dto.EmployeeResponseDto;
import ru.artemlychko.spring.rest.dto.EmployeeUpdateDto;
import ru.artemlychko.spring.rest.entity.Employee;
import ru.artemlychko.spring.rest.exceptions.NoSuchElementException;
import ru.artemlychko.spring.rest.mapper.EmployeeMapper;
import ru.artemlychko.spring.rest.repository.EmployeeRepository;

import java.util.List;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    public EmployeeResponseDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Employee with id " + id + " not found"));
        return employeeMapper.toEmployeeResponseDto(employee);
    }

    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(employeeMapper::toEmployeeResponseDto)
                .toList();
    }

    public void createEmployee(EmployeeCreateDto employeeCreateDto) {
        Employee employee = employeeMapper.toEmployee(employeeCreateDto);
        if (employee.getFirstName() == null || employee.getLastName() == null) {
            throw new IllegalArgumentException("Employee fields cannot be empty");
        }
        employeeRepository.save(employee);
    }

    public void updateEmployee(EmployeeUpdateDto employeeUpdateDto) {
        Employee employee = employeeRepository.findById(employeeUpdateDto.getId()).orElseThrow(() -> new NoSuchElementException(
                String.format("Employee with id '%s' not found", employeeUpdateDto.getId()))
        );
        if (employeeUpdateDto.getFirstName() == null || employeeUpdateDto.getLastName() == null || employeeUpdateDto.getDepartment() == null) {
            throw new IllegalArgumentException("Employee fields cannot be empty");
        } else {
            employee.setFirstName(employeeUpdateDto.getFirstName());
            employee.setLastName(employeeUpdateDto.getLastName());
            employee.setDepartment(employeeUpdateDto.getDepartment());
        }
        employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        if (employeeRepository.findById(id).isPresent()) {
            employeeRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Employee with id " + id + " not found");
        }
    }
}

