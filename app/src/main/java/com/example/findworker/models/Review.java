package com.example.findworker.models;

public class Review {
    private String shortProblemDescription;
    private String userFeedback;
    private Double userRating;
    private String userEmail;
    public Review(){}
    public Review(String shortProblemDescription, String userFeedback, Double userRating, String userEmail) {
        this.shortProblemDescription = shortProblemDescription;
        this.userFeedback = userFeedback;
        this.userRating = userRating;
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getShortProblemDescription() {
        return shortProblemDescription;
    }

    public String getUserFeedback() {
        return userFeedback;
    }

    public Double getUserRating() {
        return userRating;
    }
}
