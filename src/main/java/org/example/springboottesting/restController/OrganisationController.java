package org.example.springboottesting.restController;

import jakarta.servlet.http.HttpSession;
import org.example.springboottesting.DTO.OrganisationDTO;
import org.example.springboottesting.service.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/organisation")
public class OrganisationController {

    private final OrganisationService organisationService;

    @Autowired
    public OrganisationController(OrganisationService organisationService) {
        this.organisationService = organisationService;
    }

    @GetMapping("/")
    public ResponseEntity<OrganisationDTO> getOrganisationDetails(HttpSession session) {

        OrganisationDTO organisation = organisationService.getOrganisationDetails(
                Long.parseLong(session.getAttribute("id").toString())
        );
        if (organisation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(organisation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganisationDTO> getOrganisationDetailsById(@PathVariable long id) {

        OrganisationDTO organisation = organisationService.getOrganisationDetails(id);
        if (organisation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(organisation);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrganisationDTO>> getAllOrganisations() {

        List<OrganisationDTO> organisationsData = organisationService.getOrganisationSDetails();

        if (organisationsData == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(organisationsData);

    }
}
