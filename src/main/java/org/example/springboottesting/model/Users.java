package org.example.springboottesting.model;

import jakarta.persistence.*;
import org.springframework.context.annotation.Primary;

@Entity
public class Users {

    @Id
    @Column(unique=true)
    private String email;

    private String password;

    private String role;

    private int Id;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

}
