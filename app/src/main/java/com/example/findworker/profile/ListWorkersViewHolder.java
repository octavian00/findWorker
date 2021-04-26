package com.example.findworker.profile;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findworker.R;

public class ListWorkersViewHolder extends RecyclerView.ViewHolder {
    private TextView tv_username,tv_jobTitle;
    public ListWorkersViewHolder(@NonNull View itemView) {
        super(itemView);
        initializeViews();
    }
    private void initializeViews(){
        tv_username = itemView.findViewById(R.id.tv_row_username);
        tv_jobTitle = itemView.findViewById(R.id.tv_row_job_title);
    }
    public void setValues(String username, String jobTitle){
        tv_username.setText(username);
        tv_jobTitle.setText(jobTitle);
    }
}
