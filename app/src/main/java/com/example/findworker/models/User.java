package com.example.findworker.models;

public class User {
    protected String email;
    protected String username;
    protected String location;

    public User() {
    }

    public User(String email) {
        this.email = email;
    }

    public User(String email, String username, String location) {
        this.email = email;
        this.username = username;
        location = location;
    }

    public String getLocation() {
        return location;
    }

    public User(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
