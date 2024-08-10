package ru.artemlychko.spring.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.artemlychko.spring.rest.dto.DepartmentDTO;
import ru.artemlychko.spring.rest.entity.Department;
import ru.artemlychko.spring.rest.mapper.DepartmentMapper;
import ru.artemlychko.spring.rest.repository.DepartmentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentMapper departmentMapper;

    public DepartmentDTO getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Department not found"));
        return departmentMapper.toDto(department);
    }

    public List<DepartmentDTO> getAllDepartments() {
        List<DepartmentDTO> departmentDTOList = departmentRepository.findAll().stream()
                .map(departmentMapper::toDto)
                .collect(Collectors.toList());
        if (departmentDTOList.isEmpty()) {
            throw new IllegalArgumentException("List is empty");
        }
        return departmentDTOList;
    }

    public void createDepartment(DepartmentDTO departmentDTO) {
        Department department = departmentMapper.toEntity(departmentDTO);
        if (department.getName() == null) {
            throw new IllegalArgumentException("Department fields cannot be empty");
        }
        departmentRepository.save(department);
    }

    public void updateDepartment(DepartmentDTO departmentDTO) {
        Department department = departmentRepository.findById(departmentDTO.getId()).orElseThrow(() -> new IllegalArgumentException(
                String.format("Departemnt with id '%s' not found", departmentDTO.getId()))
        );
        if (departmentDTO.getName() != null) {
            department.setName(departmentDTO.getName());
        }
        departmentRepository.save(department);
    }

    public void deleteDepartment(Long id) {
        if (departmentRepository.findById(id).isPresent()) {
            departmentRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException(String.format("Department with id '%s' not found", id));
        }
    }
}
