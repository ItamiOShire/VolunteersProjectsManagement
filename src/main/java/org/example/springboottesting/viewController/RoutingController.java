package org.example.springboottesting.viewController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class RoutingController {

    @GetMapping("/")
    public ModelAndView getHomeView() {
        return new ModelAndView("index");
    }

    @GetMapping("/contact")
    public ModelAndView getContactView() {return  new ModelAndView("contact");}

    @GetMapping("/organisation/{id}/profile")
    public ModelAndView getOrganisationProfile(@PathVariable int id) {return new ModelAndView("organisation_light_profile");}

    @GetMapping("/organisations")
    public ModelAndView getOrganisations() {return new ModelAndView("organisations");}

    @GetMapping("/login")
    public ModelAndView getLoginView() {
        return new ModelAndView("login");
    }

    @GetMapping("/register")
    public ModelAndView getRegisterView() {
        return new ModelAndView("register");
    }

    @GetMapping("/home")
    public ModelAndView getHomeView2() {
        return new ModelAndView("index");
    }

    @GetMapping("/volunteer/projects")
    public ModelAndView getVolunteerHomeView() {
        return new ModelAndView("volunteer_home");
    }

    @GetMapping("/volunteer/projects/{id}")
    public ModelAndView getVolunteerJoinProjectView(@PathVariable Long id) {
        return new ModelAndView("join_project");
    }

    @GetMapping("/volunteer/profile")
    public ModelAndView getVolunteerProfileView() {
        return new ModelAndView("volunteer_profile");
    }

    @GetMapping("/volunteer/profile/project/{id}")
    public ModelAndView getVolunteerProjectDescView(@PathVariable Long id) {
        return new ModelAndView("volunteer_project_desc");
    }

    @GetMapping("/organisation/projects")
    public ModelAndView getOrganisationHomeView() {
        return new ModelAndView("organisation_home");
    }

    @GetMapping("/organisation/projects/{id}")
    public ModelAndView getOrganisationProjectDescView(@PathVariable Long id) {
        return new ModelAndView("organisation_project_desc");
    }

    @GetMapping("/organisation/projects/edit/desc/{id}")
    public ModelAndView getOrganisationProjectDescEditView(@PathVariable Long id) {
        return new ModelAndView("edit_project_desc");
    }

    @GetMapping("/organisation/projects/edit/tasks/{id}")
    public ModelAndView getOrganisationProjectTasksEditView(@PathVariable Long id) {
        return new ModelAndView("edit_project_tasks");
    }

    @GetMapping("/organisation/profile")
    public ModelAndView getOrganisationProfileView() {
        return new ModelAndView("organisation_profile");
    }

    @GetMapping("organisation/profile/edit/desc")
    public ModelAndView getOrganisationProfileProjectView() {
        return new ModelAndView("organisation_profile_edit");
    }

    @GetMapping("/organisation/project/create")
    public ModelAndView getOrganisationProjectCreateView() {
        return new ModelAndView("create_project");
    }

}
