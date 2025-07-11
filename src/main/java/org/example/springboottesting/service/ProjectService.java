package org.example.springboottesting.service;

import org.example.springboottesting.model.Project;
import org.example.springboottesting.DTO.ProjectDTO;
import org.example.springboottesting.model.Tag;
import org.example.springboottesting.model.Volunteer;
import org.example.springboottesting.repository.OrganisationRepository;
import org.example.springboottesting.repository.ProjectRepository;
import org.example.springboottesting.repository.TagRepository;
import org.example.springboottesting.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;
    private final OrganisationRepository organisationRepository;
    private final VolunteerRepository volunteerRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, TagRepository tagRepository, OrganisationRepository organisationRepository, VolunteerRepository volunteerRepository) {
        this.projectRepository = projectRepository;
        this.tagRepository = tagRepository;
        this.organisationRepository = organisationRepository;
        this.volunteerRepository = volunteerRepository;
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

        Set<Tag> tagSet = tagRepository.findAllByIdSorted(tags);
        project.setTags(tagSet);
        Project createdProject = projectRepository.save(project);

        return createdProject;
    }

    public List<Project> getAllProjects() {

        return projectRepository.findAllI();
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

    public boolean joinProject(Long projectId, Long volunteerId) {

        Volunteer volunteer = volunteerRepository.findById(volunteerId).orElse(null);

        Project project = projectRepository.findById(projectId).orElse(null);

        if (volunteer != null && project != null) {
            volunteer.getProjects().add(project);
            volunteerRepository.save(volunteer);
            return true;
        } else {
            return false;
        }

    }

    public List<ProjectDTO> getProjectsByVolunteerId(Long volunteerId) {

        try {

            List<Project> projects = projectRepository.findByVolunteerId(volunteerId);
            List<ProjectDTO> projectsDTO = new ArrayList<>();

            for(Project project : projects) {
                ProjectDTO projectDTO = new ProjectDTO();

                String orgName = organisationRepository.getNameById(project.getOrganisationId());

                projectDTO.setDesc(project.getDesc());
                projectDTO.setImgPath(project.getImgPath());
                projectDTO.setTitle(project.getTitle());
                projectDTO.setTags(project.getTags());
                projectDTO.setOrgName(orgName);
                projectDTO.setId(project.getId());

                projectsDTO.add(projectDTO);
            }

            return projectsDTO;

        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Project updateProject(Long projectId,
                                 String title,
                                 String desc,
                                 String imgPath,
                                 List<Long> tags) {

        Project project = projectRepository.findById(projectId).orElse(null);

        if (project != null) {

            project.setTitle(title);
            project.setDesc(desc);
            if (!imgPath.isEmpty()) {
                project.setImgPath(imgPath);
            }
            Set<Tag> tagSet = tagRepository.findAllByIdSorted(tags);
            project.setTags(tagSet);


            return  projectRepository.save(project);
        }

        return null;

    }

    public List<ProjectDTO> getSearchedProjects(String word) {

        Set<Project> projects = projectRepository.findProjectsByWord(word);

        List<ProjectDTO> projectsDTO = new ArrayList<>();

        for(Project project : projects) {

            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setDesc(project.getDesc());
            projectDTO.setImgPath(project.getImgPath());
            projectDTO.setTitle(project.getTitle());
            projectDTO.setTags(project.getTags());
            projectDTO.setId(project.getId());

            projectsDTO.add(projectDTO);
        }

        return projectsDTO;

    }

}
