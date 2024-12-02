package org.example.springboottesting.DTO;

public class VolunteerDTO {

    private String fnameAndLname;

    private String email;

    private String phone;

    private int age;

    public String getFnameAndLname() {
        return fnameAndLname;
    }

    public void setFnameAndLname(String fnameAndLname) {
        this.fnameAndLname = fnameAndLname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
