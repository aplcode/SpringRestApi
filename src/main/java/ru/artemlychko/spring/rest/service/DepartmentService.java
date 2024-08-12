package ru.artemlychko.spring.rest.service;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.artemlychko.spring.rest.dto.DepartmentCreateDto;
import ru.artemlychko.spring.rest.dto.DepartmentResponseDto;
import ru.artemlychko.spring.rest.dto.DepartmentUpdateDto;
import ru.artemlychko.spring.rest.entity.Department;
import ru.artemlychko.spring.rest.exceptions.NoSuchElementException;
import ru.artemlychko.spring.rest.mapper.DepartmentMapper;
import ru.artemlychko.spring.rest.repository.DepartmentRepository;

import java.util.List;

@Service
@Transactional
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final DepartmentMapper departmentMapper;

    public DepartmentService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    public DepartmentResponseDto getDepartmentById(Long id) {
        return departmentMapper.toDepartmentResponseDto(departmentRepository.findById(id).orElseThrow(()->
                new NoSuchElementException("Department with id " + id + " not found")));
    }

    public List<DepartmentResponseDto> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(departmentMapper::toDepartmentResponseDto)
                .toList();
    }

    public void createDepartment(DepartmentCreateDto departmentCreateDto) {
        Department department = departmentMapper.toDepartment(departmentCreateDto);
        if (department.getName() == null) {
            throw new IllegalArgumentException("Department fields cannot be empty");
        }
        departmentRepository.save(department);
    }

    public void updateDepartment(DepartmentUpdateDto departmentUpdateDto) {
        Department department = departmentRepository.findById(departmentUpdateDto.getId()).orElseThrow(() -> new NoSuchElementException(
                String.format("Departemnt with id '%s' not found", departmentUpdateDto.getId()))
        );
        department.setName(departmentUpdateDto.getName());
        departmentRepository.save(department);
    }

    public void deleteDepartment(Long id) {
        if (departmentRepository.findById(id).isPresent()) {
            departmentRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Department with id " + id + " not found");
        }
    }
}
