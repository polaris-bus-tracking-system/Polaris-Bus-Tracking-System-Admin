package com.company.polaris_bustrackingsystem.Models;

public class MainModel {
    String name;
    String enroll;
    String busstop;
    String imgurl;
    String email;

    MainModel(){

    }


    public MainModel(String name, String enroll, String busstop, String imgurl,String email) {
        this.name = name;
        this.enroll = enroll;
        this.busstop = busstop;
        this.imgurl = imgurl;
        this.email=email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnroll() {
        return enroll;
    }

    public void setEnroll(String enroll) {
        this.enroll = enroll;
    }

    public String getBusstop() {
        return busstop;
    }

    public void setBusstop(String busstop) {
        this.busstop = busstop;
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


}
