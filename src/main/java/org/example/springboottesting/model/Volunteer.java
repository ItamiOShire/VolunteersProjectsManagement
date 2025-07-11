package org.example.springboottesting.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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
    private Set<Project> projects;

    @ManyToMany
    @JoinTable(
            name = "Volunteer_Task",
            joinColumns = @JoinColumn(name = "volunteerID"),
            inverseJoinColumns = @JoinColumn(name = "taskID")
    )
    private Set<Task> tasks;

    @ManyToMany
    @JoinTable(
            name = "Task_Suggestions",
            joinColumns = @JoinColumn(name = "volunteerID"),
            inverseJoinColumns = @JoinColumn(name = "taskID")
    )
    private Set<Task> suggestedTasks;

    public Set<Task> getSuggestedTasks() {
        return suggestedTasks;
    }

    public void setSuggestedTasks(Set<Task> suggestedTasks) {
        this.suggestedTasks = suggestedTasks;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
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
