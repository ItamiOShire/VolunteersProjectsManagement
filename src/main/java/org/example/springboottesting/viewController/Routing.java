package org.example.springboottesting.viewController;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class Routing implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("index");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/volunteer/projects").setViewName("volunteer_home");
        registry.addViewController("/volunteer/projects/{id}").setViewName("join_project");
        registry.addViewController("/volunteer/profile").setViewName("volunteer_profile");
        registry.addViewController("/volunteer/profile/project/{id}").setViewName("volunteer_project_desc");
        registry.addViewController("/organisation/projects").setViewName("organisation_home");
        registry.addViewController("/organisation/projects/{id}").setViewName("organisation_project_desc");
        registry.addViewController("/organisation/projects/edit/desc/{id}").setViewName("edit_project_desc");
        registry.addViewController("/organisation/projects/edit/tasks/{id}").setViewName("edit_project_tasks");
        registry.addViewController("/organisation/profile").setViewName("organisation_profile");
        registry.addViewController("/organisation/profile/edit/desc").setViewName("organisation_profile_edit");
        registry.addViewController("/organisation/project/create").setViewName("create_project");
    }
}
