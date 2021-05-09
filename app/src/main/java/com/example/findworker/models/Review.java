package com.example.findworker.models;

public class Review {
    private String shortProblemDescription;
    private String userFeedback;
    private Double userRating;
    private String username;
    public Review(){}
    public Review(String shortProblemDescription, String userFeedback,
                  Double userRating, String userEmail) {
        this.shortProblemDescription = shortProblemDescription;
        this.userFeedback = userFeedback;
        this.userRating = userRating;
        this.username = userEmail;
    }

    public String getUsername() {
        return username;
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
