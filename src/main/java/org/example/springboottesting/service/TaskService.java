package org.example.springboottesting.service;

import jakarta.transaction.Transactional;
import org.example.springboottesting.model.Task;
import org.example.springboottesting.model.Volunteer;
import org.example.springboottesting.repository.TaskRepository;
import org.example.springboottesting.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final VolunteerRepository volunteerRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, VolunteerRepository volunteerRepository) {
        this.taskRepository = taskRepository;
        this.volunteerRepository = volunteerRepository;
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

    public List<Task> getTasksByVolunteerId(Long volunteerId) {

        Volunteer volunteer = volunteerRepository.findById(volunteerId).orElse(null);

        if (volunteer != null) {

            Set<Task> volunteerTasks = volunteer.getTasks();

            List<Task> sortedTasks = volunteerTasks.stream().sorted((o1, o2) -> o1.getDeadline().compareTo(o2.getDeadline())).toList();

            return sortedTasks;
        }

        return null;
    }
}
