package com.example.findworker.profile;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findworker.R;
import com.example.findworker.models.Review;

public class ListReviewsViewHolder extends RecyclerView.ViewHolder {
    public ListReviewsViewHolder(@NonNull View itemView) {
        super(itemView);
        initializeViews();
    }
    private TextView tv_shortDescription,tv_problem,tv_username;
    RatingBar ratingBar;

    private void initializeViews(){
        tv_shortDescription = itemView.findViewById(R.id.tv_review_userFeedback);
        tv_problem = itemView.findViewById(R.id.tv_review_shortProblem);
        tv_username = itemView.findViewById(R.id.tv_review_username);
        ratingBar = itemView.findViewById(R.id.rb_review_userRating);
    }
    public void setValues(Review review){
        tv_shortDescription.setText(review.getShortProblemDescription());
        tv_problem.setText(review.getUserFeedback());
        tv_username.setText(review.getUsername());
        ratingBar.setRating(review.getUserRating().floatValue());
    }
}
