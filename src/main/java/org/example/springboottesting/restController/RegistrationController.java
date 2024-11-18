package org.example.springboottesting.restController;

import org.example.springboottesting.model.*;
import org.example.springboottesting.service.RegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    private final RegisterUserService registerUserService;

    @Autowired
    public RegistrationController(RegisterUserService registerUserService) {
        this.registerUserService = registerUserService;
    }

    @PostMapping("/volunteer")
    public ResponseEntity<String> RegisterVolunteer(@RequestBody UserVolunteer body) {
        try {
            Users user = body.getUser();
            Volunteer volunteer = body.getVolunteer();

            registerUserService.registerVolunteer(volunteer, user);

            return ResponseEntity.ok("Volunteer registered successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/organisation")
    public ResponseEntity<String> RegisterOrganisation(@RequestBody UserOrganisation body) {

        try {
            Users user = body.getUser();
            Organisation organisation = body.getOrganisation();

            registerUserService.registerOrganisation(organisation, user);

            return ResponseEntity.ok("Organisation registered successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
