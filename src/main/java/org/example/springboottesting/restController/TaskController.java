package org.example.springboottesting.restController;


import org.example.springboottesting.model.Task;
import org.example.springboottesting.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveTasks(@RequestBody List<Task> tasks) {

        List<Task> savedTasks = taskService.saveTasks(tasks);
        if (!savedTasks.isEmpty()) {
            return ResponseEntity.ok("Zadania zostały zapisane.");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Błąd serwera, zadania nie zostały zapisane, spróbuj jeszcze raz za kilka minut.");
    }

}
