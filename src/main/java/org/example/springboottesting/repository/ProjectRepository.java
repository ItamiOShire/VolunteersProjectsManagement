package org.example.springboottesting.repository;

import org.example.springboottesting.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
