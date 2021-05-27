package com.example.findworker.models;

public class Order {
    String description;
    String location;
    String person;
    String date;
    String userName;

    public Order(String description, String location, String person, String date, String userName) {
        this.description = description;
        this.location = location;
        this.person = person;
        this.date = date;
        this.userName = userName;
    }

    public Order() {
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getPerson() {
        return person;
    }

    public String getDate() {
        return date;
    }

    public String getUserName() {
        return userName;
    }
}
