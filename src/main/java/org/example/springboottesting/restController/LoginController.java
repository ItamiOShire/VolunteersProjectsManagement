package org.example.springboottesting.restController;
import jakarta.servlet.http.HttpSession;
import org.example.springboottesting.model.LoginUser;
import org.example.springboottesting.model.Users;
import org.example.springboottesting.security.MyUserDetails;
import org.example.springboottesting.service.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final LoginUserService loginUserService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginController(LoginUserService loginUserService, AuthenticationManager authenticationManager) {
        this.loginUserService = loginUserService;
        this.authenticationManager = authenticationManager;
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

    @PostMapping("auth")
    public ResponseEntity<String> auth(@RequestBody LoginUser loginUser, HttpSession httpSession) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword())
            );

            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            httpSession.setAttribute("id", userDetails.getId());
            httpSession.setAttribute("role", userDetails.getRole());

            return ResponseEntity.ok(userDetails.getRole());
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage() + " " + loginUser.getEmail() + " " + loginUser.getPassword());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid data");
        }
    }
}
