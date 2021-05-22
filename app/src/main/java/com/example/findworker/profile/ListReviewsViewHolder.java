package com.example.findworker.profile;

import android.graphics.Typeface;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findworker.R;
import com.example.findworker.models.Review;

public class ListReviewsViewHolder extends RecyclerView.ViewHolder {
    private TextView tv_shortDescription,tv_problem,tv_username;
    RatingBar ratingBar;

    public ListReviewsViewHolder(@NonNull View itemView) {
        super(itemView);
        initializeViews();
    }
    private void initializeViews(){
        tv_shortDescription = itemView.findViewById(R.id.tv_review_userFeedback);
        tv_problem = itemView.findViewById(R.id.tv_review_shortProblem);
        tv_username = itemView.findViewById(R.id.tv_review_username);
        ratingBar = itemView.findViewById(R.id.rb_review_userRating);
    }
    public void setValues(Review review){
        String SHORT_DESCRIPTION = "Short Description: ";
        tv_shortDescription.setText(String.format("%s%s", SHORT_DESCRIPTION, review.getShortProblemDescription()));
        String FEEDBACK = "User Feedback: ";
        tv_problem.setText(String.format("%s%s", FEEDBACK, review.getUserFeedback()));
        String USERNAME = "Username: ";
        tv_username.setText(String.format("%s%s", USERNAME, review.getUsername()));
        ratingBar.setRating(review.getUserRating().floatValue());
    }
}
