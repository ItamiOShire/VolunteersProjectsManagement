package org.example.springboottesting.restController;


import jakarta.servlet.http.HttpSession;
import org.example.springboottesting.model.LoginUser;
import org.example.springboottesting.model.Users;
import org.example.springboottesting.service.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final LoginUserService loginUserService;

    @Autowired
    public LoginController(LoginUserService loginUserService) {
        this.loginUserService = loginUserService;
    }

    @PostMapping("/")
    public ResponseEntity<String> loginUser(@RequestBody LoginUser loginUser, HttpSession httpSession) {

        Users user = loginUserService.checkUser(loginUser);
        if (user != null) {
            httpSession.setAttribute("id",user.getId());
            return ResponseEntity.ok(user.getRole());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid data");
    }

    @GetMapping("/getId")
    public ResponseEntity<String> getId(HttpSession httpSession) {
        return ResponseEntity.ok(httpSession.getAttribute("id").toString());
    }

}
