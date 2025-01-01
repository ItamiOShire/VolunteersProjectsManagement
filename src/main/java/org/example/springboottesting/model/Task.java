package org.example.springboottesting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty("task-title")
    private String title;

    @JsonProperty("task-desc")
    @Column(name = "descr")
    private String desc;

    @JsonProperty("priority")
    @Column(name = "priorityID")
    private int priorityId;

    @Column(name = "projectID")
    private int projectId;

    private LocalDate deadline;

    @ManyToMany(mappedBy = "tasks", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Volunteer> volunteers;

    @ManyToMany(mappedBy = "suggestedTasks", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Volunteer> suggestedVolunteers;

    public Set<Volunteer> getSuggestedVolunteers() {
        return suggestedVolunteers;
    }

    public void setSuggestedVolunteers(Set<Volunteer> suggestedVolunteers) {
        this.suggestedVolunteers = suggestedVolunteers;
    }

    public Set<Volunteer> getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(Set<Volunteer> volunteers) {
        this.volunteers = volunteers;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(int priorityId) {
        this.priorityId = priorityId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
