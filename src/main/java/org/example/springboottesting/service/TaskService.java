package org.example.springboottesting.service;

import org.example.springboottesting.model.Task;
import org.example.springboottesting.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> saveTasks(List<Task> tasks) {

        return taskRepository.saveAll(tasks);

    }
}
