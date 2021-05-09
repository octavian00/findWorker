package com.example.findworker.profile;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findworker.R;
import com.example.findworker.models.Review;

import java.util.List;

public class ListReviewAdapter extends RecyclerView.Adapter<ListReviewsViewHolder> {
    private Context context;
    private List<Review> reviewList;
    public ListReviewAdapter(List<Review> reviewList){
        this.reviewList = reviewList;
    }
    @NonNull
    @Override
    public ListReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View contactView = layoutInflater.inflate(R.layout.review_row,parent,false);
        return new ListReviewsViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListReviewsViewHolder holder, int position) {
        final Review review = reviewList.get(position);
        Log.d("REVIEWADAPTER","list size="+reviewList.size());
        Log.d("REVIEWADAPTER", String.valueOf(review.getUserRating()));
        holder.setValues(review);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }
}
