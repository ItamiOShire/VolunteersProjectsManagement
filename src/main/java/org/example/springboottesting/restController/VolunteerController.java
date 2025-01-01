package org.example.springboottesting.restController;

import jakarta.servlet.http.HttpSession;
import org.example.springboottesting.DTO.VolunteerDTO;
import org.example.springboottesting.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/volunteer")
public class VolunteerController {

    private final VolunteerService volunteerService;

    @Autowired
    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @GetMapping("/")
    public ResponseEntity<VolunteerDTO> getVolunteerData(HttpSession session) {

        VolunteerDTO volunteerDTO = volunteerService.getVolunteerDetails(
                Long.parseLong(session.getAttribute("id").toString())
        );

        if(volunteerDTO != null) {
            return ResponseEntity.ok(volunteerDTO);
        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/project/{id}")
    public ResponseEntity<List<VolunteerDTO>> getVolunteerDataByProject(@PathVariable int id) {

        List<VolunteerDTO> volunteers = volunteerService.getVolunteersByJoinedProject(id);

        if(volunteers != null) {
            return ResponseEntity.ok(volunteers);
        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/withTasks/{taskId}/project/{projectId}")
    public ResponseEntity<List<VolunteerDTO>> getVolunteerDataWithTasksByProject(@PathVariable int taskId, @PathVariable int projectId) {

        List<VolunteerDTO> volunteers = volunteerService.getVolunteersWithTasksByProject(projectId, taskId );

        if(volunteers != null) {
            return ResponseEntity.ok(volunteers);
        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/withSuggestedTasks/{taskId}/project/{projectId}")
    public ResponseEntity<List<VolunteerDTO>> getVolunteerDataWithSuggestedTasksByProject(@PathVariable int taskId, @PathVariable int projectId) {

        List<VolunteerDTO> volunteers = volunteerService.getVolunteersWithSuggestedTasksByProject(projectId, taskId );

        if(volunteers != null) {
            return ResponseEntity.ok(volunteers);
        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/withTasksToDelete/{taskId}/project/{projectId}")
    public ResponseEntity<List<VolunteerDTO>> getVolunteerDataWithTasksToDeleteByProject(@PathVariable int taskId, @PathVariable int projectId) {

        List<VolunteerDTO> volunteers = volunteerService.getVolunteersWithTasksToDelete(projectId, taskId );

        if(volunteers != null) {
            return ResponseEntity.ok(volunteers);
        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/{volunteerId}/task/{taskId}")
    public ResponseEntity<String> addVolunteerToTask(@PathVariable Long volunteerId, @PathVariable Long taskId) {

        boolean isAdded = volunteerService.addVolunteerToTask(volunteerId, taskId);

        if(isAdded) {
            return ResponseEntity.ok("Dodano");
        }

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{volunteerId}/task/{taskId}")
    public ResponseEntity<String> deleteVolunteerFromTask(@PathVariable Long volunteerId, @PathVariable Long taskId) {

        boolean isDeleted = volunteerService.deleteVolunteerFromTask(volunteerId, taskId);

        if(isDeleted) {
            return ResponseEntity.ok("Usunięto");
        }

        return ResponseEntity.notFound().build();

    }

}
