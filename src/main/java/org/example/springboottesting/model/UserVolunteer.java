package org.example.springboottesting.model;

public class UserVolunteer {

    private Users user;
    private Volunteer volunteer;

    public UserVolunteer() {}

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }
}
