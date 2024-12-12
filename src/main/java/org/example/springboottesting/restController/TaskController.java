package org.example.springboottesting.restController;


import org.example.springboottesting.model.Task;
import org.example.springboottesting.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<List<Task>> saveTasks(@RequestBody List<Task> tasks) {

        List<Task> savedTasks = taskService.saveTasks(tasks);
        if (!savedTasks.isEmpty()) {
            return ResponseEntity.ok(savedTasks);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<List<Task>> getTask(@PathVariable int id) {

        List<Task> tasks = taskService.getTasksByProjectId(id);

        if (!tasks.isEmpty()) {
            return ResponseEntity.ok(tasks);
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{taskId}/update/{priorityId}")
    public ResponseEntity<String> updateTaskPriority(@PathVariable int taskId, @PathVariable int priorityId) {

        System.out.println(taskId + " " + priorityId);
        Task task = taskService.updateTask(taskId, priorityId);

        System.out.println(task.getPriorityId());

        if (task != null) {
            return ResponseEntity.ok("Zapisano!");
        }

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteTask(@RequestBody List<Long> taskIds) {

        boolean isDeleted = taskService.deleteTask(taskIds);

        if (isDeleted) {
            return ResponseEntity.ok("Usunięto!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Usuwanie nie powiodło się");
    }
}
