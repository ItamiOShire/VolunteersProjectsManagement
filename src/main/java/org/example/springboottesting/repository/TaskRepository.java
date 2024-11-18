package org.example.springboottesting.repository;

import org.example.springboottesting.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
