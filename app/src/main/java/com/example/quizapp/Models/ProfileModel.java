package com.example.quizapp.Models;

public class ProfileModel {

    private String name;
    private String email;
    private String phone;
    private int bmCount;

    public ProfileModel(String name, String email, String phone, int bmCount) {
        this.name = name;
        this.email = email;
        this.phone=phone;
        this.bmCount=bmCount;
    }

    public int getBmCount() {
        return bmCount;
    }

    public void setBmCount(int bmCount) {
        this.bmCount = bmCount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
