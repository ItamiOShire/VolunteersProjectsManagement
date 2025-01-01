package org.example.springboottesting.restController;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/volunteer")
    public ResponseEntity<List<Task>> getTasksByVolunteerId(HttpSession session) {

        List<Task> tasks = taskService.getTasksByVolunteerId(Long.parseLong(session.getAttribute("id").toString()));

        if (!tasks.isEmpty()) {
            return ResponseEntity.ok(tasks);
        }
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/volunteer/project/{id}")
    public ResponseEntity<List<Task>> getTasksByVolunteerIdAndProjectId(@PathVariable int id, HttpSession session) {

        System.out.println(id + " " + session.getAttribute("id").toString());
        List<Task> tasks = taskService.getTasksByVolunteerIdAndProjectId (Integer.parseInt(session.getAttribute("id").toString()), id);

        System.out.println(tasks);

        if (tasks != null) {
            return ResponseEntity.ok(tasks);
        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/notAttended/volunteer/project/{id}")
    public ResponseEntity<List<Task>> getTasksNotAttendedByVolunteerByVolunteerIdAndProjectId(@PathVariable int id, HttpSession session) {

        List<Task> tasks = taskService.getTasksNotAttendedByVolunteer(session.getAttribute("id").toString(), id);

        if (!tasks.isEmpty()) {
            return ResponseEntity.ok(tasks);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/suggested/volunteer/project/{id}")
    public ResponseEntity<List<Task>> getSuggestedTasksByVolunteerIdAndProjectId(@PathVariable int id, HttpSession session) {

        List<Task> tasks = taskService.getVolunteersSuggestedTasks(session.getAttribute("id").toString(), id);

        if (!tasks.isEmpty()) {
            return ResponseEntity.ok(tasks);
        }

        return ResponseEntity.notFound().build();

    }

    @PutMapping("{taskId}/suggested/volunteer")
    public ResponseEntity<String> saveSuggestedTask(@PathVariable Long taskId, HttpSession session) {

        boolean isSaved = taskService.saveSuggestedTask(taskId, session.getAttribute("id").toString());

        if (isSaved) {
            return ResponseEntity.ok("Zgłoszono chęć uczestniczenia");
        }

        return ResponseEntity.notFound().build();
    }


}
