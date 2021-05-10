package com.example.findworker.models;

import java.util.ArrayList;

public class WorkerOrders extends Worker {
    private ArrayList<String> pendingOrders;
    private ArrayList<Review> reviews;
    private Double average;
    private Integer numberOfReviews;
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public WorkerOrders(){}
    public WorkerOrders(String email, String username, String jobTitle,Integer experience,String location,ArrayList<String> pendingOrders){
        super(email,username,jobTitle,experience,location);
        this.pendingOrders = pendingOrders;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public void addReview(Review r){
        if(this.reviews == null){
            this.reviews = new ArrayList<>();
        }
        this.reviews.add(r);
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public ArrayList<String> getPendingOrders() {
        return pendingOrders;
    }

    public void setPendingOrders(ArrayList<String> pendingOrders) {
        this.pendingOrders = pendingOrders;
    }
    public void removePendingOrders(int position){
        this.pendingOrders.remove(position);
    }

    public  void addUserOrder(String currentUUID){
        this.pendingOrders.add(currentUUID);
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public Integer getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(Integer numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }
}
