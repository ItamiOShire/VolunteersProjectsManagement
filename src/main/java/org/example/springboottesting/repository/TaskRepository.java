package org.example.springboottesting.repository;

import org.example.springboottesting.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // TODO: add manually implementation of deleting only link between Volunteer_Task
    List<Task> getTasksByProjectId(int projectId);
}
