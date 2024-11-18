package org.example.springboottesting.service;


import org.example.springboottesting.model.LoginUser;
import org.example.springboottesting.model.Users;
import org.example.springboottesting.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginUserService {

    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;

    @Autowired
    public LoginUserService(PasswordEncoder passwordEncoder, UsersRepository usersRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
    }

    public Users checkUser(LoginUser loginUser) {
        Optional<Users> user = usersRepository.findByEmail(loginUser.getEmail());
        if (user.isPresent()) {
            user.get().setPassword(passwordEncoder.encode(loginUser.getPassword()));
            return user.get();
        }
        return null;
    }
}
