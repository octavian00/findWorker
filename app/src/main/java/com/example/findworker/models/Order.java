package com.example.findworker.models;

public class Order {
    String description;
    String location;
    String person;
    String date;
    String userUUID;

    public Order(String description, String location, String person, String date, String userUUID) {
        this.description = description;
        this.location = location;
        this.person = person;
        this.date = date;
        this.userUUID = userUUID;
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

    public String getUserUUID() {
        return userUUID;
    }
}
