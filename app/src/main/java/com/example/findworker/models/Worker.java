package com.example.findworker.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Worker extends User implements Serializable {
    private String jobTitle;
    private Integer experience;
    private String location;
    private String phoneNumber;

    public Worker(){}
    public Worker(String email, String username, String jobTitle) {
        super(email,username);
        this.jobTitle = jobTitle;
    }
    public Worker(String email, String username, String jobTitle,Integer experience,String location) {
        super(email,username);
        this.jobTitle = jobTitle;
        this.experience = experience;
        this.location = location;
    }
    public Worker(String email, String username,
                  String jobTitle, Integer experience,
                  String location, String phoneNumber) {
        super(email,username);
        this.jobTitle = jobTitle;
        this.experience = experience;
        this.location = location;
        this.phoneNumber = phoneNumber;
    }
    public Integer getExperience() {
        return experience;
    }
    public String getLocation() {
        return location;
    }

    public String getJobTitle() {
        return jobTitle;
    }
    public String showWorkersField(){
        return "username"+this.getUsername()+"userEmail"+this.getEmail()+"jobTitle"+this.getJobTitle();
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
