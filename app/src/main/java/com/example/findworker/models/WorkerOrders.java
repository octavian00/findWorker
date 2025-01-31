package com.example.findworker.models;

import java.util.ArrayList;

public class WorkerOrders extends Worker {
    private ArrayList<Order> pendingOrders;
    private ArrayList<Review> reviews;
    private ArrayList<String> finishOrders;
    private Double average;
    private Integer numberOfReviews;

    public WorkerOrders(){}
    public WorkerOrders(String email, String username, String jobTitle,Integer experience,String location,ArrayList<Order> pendingOrders){
        super(email,username,jobTitle,experience,location);
        this.pendingOrders = pendingOrders;
    }

    public WorkerOrders(String email, String username, String jobTitle, Integer experience,
                        String location, String phoneNumber, ArrayList<Order> pendingOrders,
                        ArrayList<Review> reviews, Double average, Integer numberOfReviews) {
        super(email, username, jobTitle, experience, location, phoneNumber);
        this.pendingOrders = pendingOrders;
        this.reviews = reviews;
        this.average = average;
        this.numberOfReviews = numberOfReviews;
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

    public ArrayList<Order> getPendingOrders() {
        return pendingOrders;
    }

    public void setPendingOrders(ArrayList<Order> pendingOrders) {
        this.pendingOrders = pendingOrders;
    }
    public void removePendingOrders(int position){
        this.pendingOrders.remove(position);
    }

    public  void addUserOrder(Order order){
        this.pendingOrders.add(order);
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

    public ArrayList<String> getFinishOrders() {
        return finishOrders;
    }
    public void addFinishOrder(String finishOrder){
        if(this.finishOrders == null){
            finishOrders = new ArrayList<>();
        }
        finishOrders.add(finishOrder);
    }
}
