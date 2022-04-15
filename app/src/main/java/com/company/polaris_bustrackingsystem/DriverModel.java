package com.company.polaris_bustrackingsystem;

public class DriverModel {
    String name,imgurl,email,phone;


    DriverModel()
    {

    }

    public DriverModel(String name, String imgurl, String email, String phone) {
        this.name = name;
        this.imgurl = imgurl;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
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
}
