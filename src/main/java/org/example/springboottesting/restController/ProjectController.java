package org.example.springboottesting.restController;

import jakarta.servlet.http.HttpSession;
import org.example.springboottesting.model.Project;
import org.example.springboottesting.DTO.ProjectDTO;
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

        String fileName = UUID.randomUUID().toString() + ".jpg";
        String filePath = "D:\\school\\sem7\\inzynierka\\temp\\SpringBootTesting\\src\\main\\resources\\static\\images\\" + fileName;
        String filePathToWrite = "\\images\\" + fileName;
        Project createdProject = projectService.createProject(tittle,
                desc,
                filePathToWrite,
                tags,
                Integer.parseInt(session.getAttribute("id").toString()));

        if (createdProject != null) {
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
            return ResponseEntity.ok(String.valueOf(createdProject.getId()));
        }

        return ResponseEntity.badRequest().body("nie udalo sie stworzyc projektu");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Project>> getAll() {
        List<Project> projects = projectService.getAllProjects();

        return ResponseEntity.ok(projects);
    }

    @GetMapping("organisation/all")
    public ResponseEntity<List<Project>> getAllOrganisation(HttpSession session) {
        List<Project> projects = projectService.getProjectsByOrganisationId(
                Integer.parseInt(session.getAttribute("id").toString())
        );
        for (Project project : projects) {
            System.out.println(project);
        }
        return ResponseEntity.ok(projects);
    }

    @GetMapping("organisation{id}/all")
    public ResponseEntity<List<Project>> getAllOrganisation(@PathVariable long id) {
        List<Project> projects = projectService.getProjectsByOrganisationId(
                Integer.parseInt(String.valueOf(id))
        );
        for (Project project : projects) {
            System.out.println(project);
        }
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/volunteer/all")
    public ResponseEntity<List<ProjectDTO>> getAllVolunteer(HttpSession session) {

        List<ProjectDTO> projects = projectService.getProjectsByVolunteerId(
                Long.parseLong(session.getAttribute("id").toString())
        );

        if (projects == null || projects.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(projects);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable Long id) {

        ProjectDTO project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }

    @GetMapping("/join/{id}")
    public ResponseEntity<String> joinProject(@PathVariable Long id, HttpSession session) {

        boolean isJoined = projectService.joinProject(
                id,
                Long.parseLong(session.getAttribute("id").toString())
        );

        if (isJoined) {
            return ResponseEntity.ok("Dołączono do projektu");
        } else {
            return ResponseEntity.internalServerError().body("Błąd serwera - nie znaleziono projektu lub użytkownika");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateProject(@RequestParam("title") String title,
                                                @RequestParam("desc-long") String desc,
                                                @RequestParam("img")MultipartFile img,
                                                @RequestParam("tags") List<Long> tags,
                                                @PathVariable Long id) {
        String imgPath = "";
        String filePath = "";
        String filePathToWrite = "";
        if (img.getSize() != 0L) {

            String fileName = UUID.randomUUID().toString() + ".jpg";
            filePath = "D:\\school\\sem7\\inzynierka\\temp\\SpringBootTesting\\src\\main\\resources\\static\\images\\" +"d3b605a7-dc53-4855-a6a8-6940151a00cc.jpg";
            filePathToWrite = "\\images\\" + "d3b605a7-dc53-4855-a6a8-6940151a00cc.jpg";

        }

        Project project = projectService.updateProject(id,
                title,
                desc,
                filePathToWrite,
                tags);


        if (!filePathToWrite.isEmpty()) {
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

        }

        if (project != null) {

            return ResponseEntity.ok(String.valueOf(project.getId()));

        }

        return ResponseEntity.badRequest().body("nie znaleziono projektu");

    }

    @GetMapping("/search/{word}")
    public ResponseEntity<List<ProjectDTO>> searchProjects(@PathVariable String word) {

        List<ProjectDTO> projects = projectService.getSearchedProjects(word);

        if (projects.isEmpty()) {

            return ResponseEntity.notFound().build();

        }

        return ResponseEntity.ok(projects);

    }
}
