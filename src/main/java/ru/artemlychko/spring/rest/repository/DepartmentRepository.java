package ru.artemlychko.spring.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.artemlychko.spring.rest.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
