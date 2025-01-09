package org.example.springboottesting.DTO;

public class OrganisationDTO {

    private int id;

    private String name;

    private String type;

    private String fnameAndlname;

    private String email;

    private String phone;

    private String adress;

    private String krsNumber;

    private String desc;

    private String imgPath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFnameAndlname() {
        return fnameAndlname;
    }

    public void setFnameAndlname(String fnameAndlname) {
        this.fnameAndlname = fnameAndlname;
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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getKrsNumber() {
        return krsNumber;
    }

    public void setKrsNumber(String krsNumber) {
        this.krsNumber = krsNumber;
    }
}
