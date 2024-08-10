package ru.artemlychko.spring.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.artemlychko.spring.rest.dto.EmployeeDTO;
import ru.artemlychko.spring.rest.entity.Employee;
import ru.artemlychko.spring.rest.mapper.EmployeeMapper;
import ru.artemlychko.spring.rest.repository.EmployeeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Employee not found"));
        return employeeMapper.toDto(employee);
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeDTO> employeeDTOList = employeeRepository.findAll().stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
        if (employeeDTOList.isEmpty()) {
            throw new IllegalArgumentException("List is empty");
        }
        return employeeDTOList;
    }

    public void createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.toEntity(employeeDTO);
        if (employee.getFirstName() == null || employee.getLastName() == null) {
            throw new IllegalArgumentException("Employee fields cannot be empty");
        }
        employeeRepository.save(employee);
    }

    public void updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(
                String.format("Employee with id '%s' not found", id))
        );
        if (employee.getFirstName() != null) {
            employee.setFirstName(employeeDTO.getFirstName());
        }
        if (employee.getLastName() != null) {
            employee.setLastName(employeeDTO.getLastName());
        }
        employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        if (employeeRepository.findById(id).isPresent()) {
            employeeRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException(String.format("Employee with id '%s' not found", id));
        }
    }
}

