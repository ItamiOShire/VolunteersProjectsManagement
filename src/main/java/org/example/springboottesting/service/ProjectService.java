package org.example.springboottesting.service;

import org.example.springboottesting.model.Project;
import org.example.springboottesting.model.ProjectDTO;
import org.example.springboottesting.model.Tag;
import org.example.springboottesting.repository.OrganisationRepository;
import org.example.springboottesting.repository.ProjectRepository;
import org.example.springboottesting.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;
    private final OrganisationRepository organisationRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, TagRepository tagRepository, OrganisationRepository organisationRepository) {
        this.projectRepository = projectRepository;
        this.tagRepository = tagRepository;
        this.organisationRepository = organisationRepository;
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

    public ProjectDTO getProjectById(Long id) {

        try {
            Project project = projectRepository.findById(id).orElse(null);


            assert project != null;
            String orgName = organisationRepository.getNameById(project.getOrganisationId());

            ProjectDTO projectDTO = new ProjectDTO();

            projectDTO.setDesc(project.getDesc());
            projectDTO.setImgPath(project.getImgPath());
            projectDTO.setTitle(project.getTitle());
            projectDTO.setTags(project.getTags());
            projectDTO.setOrgName(orgName);

            return projectDTO;
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            return null;
        }


    }



}
