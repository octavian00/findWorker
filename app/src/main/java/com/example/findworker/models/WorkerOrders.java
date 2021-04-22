package com.example.findworker.models;

import java.util.ArrayList;

public class WorkerOrders extends Worker {
    private ArrayList<String> pendingOrders;
    public WorkerOrders(){}
    public WorkerOrders(String email, String username, String jobTitle,Integer experience,String location,ArrayList<String> pendingOrders){
        super(email,username,jobTitle,experience,location);
        this.pendingOrders = pendingOrders;
    }
}
