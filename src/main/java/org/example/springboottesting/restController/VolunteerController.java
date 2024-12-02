package org.example.springboottesting.restController;

import jakarta.servlet.http.HttpSession;
import org.example.springboottesting.DTO.VolunteerDTO;
import org.example.springboottesting.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
