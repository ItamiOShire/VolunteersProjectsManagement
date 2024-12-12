package org.example.springboottesting.service;

import jakarta.transaction.Transactional;
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

    public List<Task> getTasksByProjectId(int projectId) {

        return taskRepository.getTasksByProjectId(projectId);

    }

    @Transactional
    public Task updateTask(int taskId, int priorityId) {

        Task task = taskRepository.findById(
                Long.parseLong(
                Integer.valueOf(taskId).toString())).orElse(null);



        if (task == null) {
            return null;
        }

        System.out.println(task.getPriorityId());

        task.setPriorityId(priorityId);

        System.out.println(task.getPriorityId());

        return taskRepository.save(task);

    }


    @Transactional
    public boolean deleteTask(List<Long> taskIds) {

        for (Long taskId : taskIds) {

            Task task = taskRepository.findById(taskId).orElse(null);

            if (task == null) {

                return false;
            }

            taskRepository.delete(task);

        }

        return true;
    }
}
