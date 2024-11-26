package org.example.springboottesting.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Volunteer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty("fname")
    private String firstName;

    @JsonProperty("lname")
    private String lastName;

    @JsonProperty("birth_day")
    private LocalDate birthDate;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String email;

    private String password;

    @ManyToMany
    @JoinTable(
            name = "Volunteer_Project",
            joinColumns = @JoinColumn(name = "volunteerID"),
            inverseJoinColumns = @JoinColumn(name = "projectID")
    )
    private List<Project> projects;

    @ManyToMany
    @JoinTable(
            name = "Volunteer_Task",
            joinColumns = @JoinColumn(name = "volunteerID"),
            inverseJoinColumns = @JoinColumn(name = "taskID")
    )
    private List<Task> tasks;

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

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

}
