package com.example.findworker.models;

import java.util.ArrayList;
import java.util.UUID;

public class Worker extends User{
    private String specialization;
    private String description;
    private ArrayList<UUID> orders;
    public Worker(String email, String password) {
        super(email, password);
    }
}
