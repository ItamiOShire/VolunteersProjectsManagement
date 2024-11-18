package org.example.springboottesting.viewController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class RoutingController {

    @GetMapping("/")
    public ModelAndView getHomeView() {
        return new ModelAndView("index");
    }

    @GetMapping("/login")
    public ModelAndView getLoginView() {
        return new ModelAndView("login");
    }

    @GetMapping("/register")
    public ModelAndView getRegisterView() {
        return new ModelAndView("register");
    }
}
