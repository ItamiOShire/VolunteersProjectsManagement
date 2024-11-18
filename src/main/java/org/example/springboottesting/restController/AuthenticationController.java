package org.example.springboottesting.restController;

import jakarta.servlet.http.HttpSession;
import org.example.springboottesting.model.Users;
import org.example.springboottesting.security.MyUserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    /*private final AuthenticationManager authenticationManager;

    public AuthenticationController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public String login(@RequestBody Users loginRequest, HttpSession session) {
        try {
            // Tworzymy token autoryzacyjny
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            // Pobieramy ID użytkownika i rolę
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            session.setAttribute("userId", userDetails.getId());
            session.setAttribute("userRole", userDetails.getRole());

            return userDetails.getRole(); // Zwracamy rolę użytkownika
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage() + " " + loginRequest.getEmail() + " " + loginRequest.getPassword());
            return "Login failed";
        }
    }

    @GetMapping("/redirect")
    public String redirectUser(HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        if ("STUDENT".equals(role)) {
            return "redirect:/student/dashboard"; // Przekierowanie do dashboardu studenta
        } else if ("PROFESSOR".equals(role)) {
            return "redirect:/professor/dashboard"; // Przekierowanie do dashboardu profesora
        }
        return "redirect:/"; // Domyślne przekierowanie
    }

    @GetMapping("/current-user-id")
    public Long getCurrentUserId(HttpSession session) {
        return (Long) session.getAttribute("userId");
    }*/
}