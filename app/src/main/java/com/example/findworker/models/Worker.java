package com.example.findworker.models;

public class Worker extends User{
    private String username;
    private String jobTitle;
    private Integer experience;
    private String location;
    
    public Worker(String email, String username, String jobTitle) {
        super(email);
        this.username = username;
        this.jobTitle = jobTitle;
    }
    public Worker(String email, String username, String jobTitle,Integer experience,String location) {
        super(email);
        this.username = username;
        this.jobTitle = jobTitle;
        this.experience = experience;
        this.location = location;
    }
    public Integer getExperience() {
        return experience;
    }
    public String getLocation() {
        return location;
    }
    public String getUsername() {
        return username;
    }

    public String getJobTitle() {
        return jobTitle;
    }
}
