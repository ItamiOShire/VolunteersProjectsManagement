package org.example.springboottesting.restController;

import jakarta.servlet.http.HttpSession;
import org.example.springboottesting.model.Tag;
import org.example.springboottesting.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/create")
    public String create(@RequestParam("title") String tittle,
                         @RequestParam("desc-long") String desc,
                         @RequestParam("img")MultipartFile img,
                         @RequestParam("tags") List<Long> tags,
                         HttpSession session) {

        String filePath = "resources/static/images";
        projectService.createProject(tittle,
                desc,
                filePath,
                tags,
                Integer.parseInt(session.getAttribute("id").toString()));
        //System.out.println(project);

        return "";
    }
}
