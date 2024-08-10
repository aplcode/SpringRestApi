package ru.artemlychko.spring.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.artemlychko.spring.rest.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
