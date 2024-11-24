package org.example.springboottesting.service;

import org.example.springboottesting.model.Project;
import org.example.springboottesting.model.Tag;
import org.example.springboottesting.repository.ProjectRepository;
import org.example.springboottesting.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, TagRepository tagRepository) {
        this.projectRepository = projectRepository;
        this.tagRepository = tagRepository;
    }

    public Project createProject(String tittle,
                                 String desc,
                                 String path,
                                 List<Long> tags,
                                 int organisationId) {
        Project project = new Project();
        project.setTitle(tittle);
        project.setDesc(desc);
        project.setImgPath(path);
        project.setOrganisationId(organisationId);

        System.out.println(tags);

        List<Tag> tagSet = tagRepository.findAllByIdSorted(tags);
        project.setTags(tagSet);
        Project createdProject = projectRepository.save(project);


        return createdProject;
    }

    public List<Project> getAllProjects() {

        List<Project> projects = projectRepository.findAll();

        return projectRepository.findAll();
    }

    public List<Project> getProjectsByOrganisationId(int id) {

        return projectRepository.findByOrganisationId(id);
    }



}
