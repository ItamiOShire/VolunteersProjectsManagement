package org.example.springboottesting.restController;

import jakarta.servlet.http.HttpSession;
import org.example.springboottesting.model.Project;
import org.example.springboottesting.model.Tag;
import org.example.springboottesting.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestParam("title") String tittle,
                                 @RequestParam("desc-long") String desc,
                                 @RequestParam("img")MultipartFile img,
                                 @RequestParam("tags") List<Long> tags,
                                 HttpSession session) {

        String filePath = "D:\\school\\sem7\\inzynierka\\temp\\SpringBootTesting\\src\\main\\resources\\static\\images\\" + UUID.randomUUID().toString() + ".jpg";
        Project createdProject = projectService.createProject(tittle,
                desc,
                filePath,
                tags,
                Integer.parseInt(session.getAttribute("id").toString()));

        if (createdProject != null) {
            try {
                Path path = Paths.get(filePath);
                Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            return ResponseEntity.ok(String.valueOf(createdProject.getId()));
        }

        return ResponseEntity.badRequest().body("nie udalo sie stworzyc projektu");
    }
}
