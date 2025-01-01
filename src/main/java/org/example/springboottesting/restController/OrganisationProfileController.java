package org.example.springboottesting.restController;


import jakarta.servlet.http.HttpSession;
import org.example.springboottesting.model.OrganisationProfile;
import org.example.springboottesting.service.OrganisationProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.EntityResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RestController
@RequestMapping("/api/organisationProfile")
public class OrganisationProfileController {

    private final OrganisationProfileService organisationProfileService;

    @Autowired
    public OrganisationProfileController(OrganisationProfileService organisationProfileService) {
        this.organisationProfileService = organisationProfileService;
    }

    @GetMapping("/")
    public ResponseEntity<OrganisationProfile> getOrganisationProfile(HttpSession session) {

        OrganisationProfile fetchedOrganisationProfile = organisationProfileService.getOrganisationProfile(session.getAttribute("id").toString());

        if (fetchedOrganisationProfile != null) {

            return ResponseEntity.ok(fetchedOrganisationProfile);

        }

        return ResponseEntity.noContent().build();

    }

    @PostMapping("/")
    public ResponseEntity<String> saveOrganisationProfile(@RequestParam("desc") String desc,
                                                          @RequestParam("img") MultipartFile img,
                                                          HttpSession session) {

        String fileName = UUID.randomUUID().toString() + ".jpg";
        String filePath = "D:\\school\\sem7\\inzynierka\\temp\\SpringBootTesting\\src\\main\\resources\\static\\images\\" +fileName;
        String filePathToWrite = "\\images\\" + fileName;

        OrganisationProfile savedOrganisationProfile = organisationProfileService.saveOrganisationProfile(session.getAttribute("id").toString(),
                desc,
                filePathToWrite);

        if (savedOrganisationProfile != null) {
            try {
                Path path = Paths.get(filePath);
                Files.copy(
                        img.getInputStream(),
                        path,
                        StandardCopyOption.REPLACE_EXISTING
                );
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            return ResponseEntity.ok("udało sie zapisać profil");
        }

        return ResponseEntity.badRequest().body("nie udało się zapisać profilu - podano błędne dane");

    }
}
