package org.example.springboottesting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @JsonProperty("desc-long")
    @Column(name = "descr")
    private String desc;

    private String imgPath;

    @Column(name="organisationID")
    private int organisationId;

    @ManyToMany
    @JoinTable(
            name = "Project_Tag",
            joinColumns = @JoinColumn(name = "projectID"),
            inverseJoinColumns = @JoinColumn(name = "tagID")
    )
    private Set<Tag> tags;

    @ManyToMany(mappedBy = "projects", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Volunteer> volunteers;

    public Set<Volunteer> getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(Set<Volunteer> volunteers) {
        this.volunteers = volunteers;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public int getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(int organisationId) {
        this.organisationId = organisationId;
    }

    @Override
    public String toString() {
        return "Project [id=" + id + ", title=" + title + ", desc=" + desc + ", imgPath=" + imgPath + ", tags=" + tags + "]";
    }
}
