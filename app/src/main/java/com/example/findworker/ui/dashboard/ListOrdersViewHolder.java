package com.example.findworker.ui.dashboard;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findworker.R;
import com.example.findworker.models.Worker;

public class ListOrdersViewHolder extends RecyclerView.ViewHolder {
    private TextView tv_userName;
    private Button btn_finish;
    private String currentUserUUID;
    public ListOrdersViewHolder(@NonNull View itemView) {
        super(itemView);
        initializeViews();
        listeners();
    }
    private void initializeViews(){
        tv_userName = itemView.findViewById(R.id.tv_userNameOrder);
        btn_finish = itemView.findViewById(R.id.btn_done);
    }
    public void setValues(String name){
        tv_userName.setText(name);
    }
    private void listeners(){
        btn_finish.setOnClickListener(v -> Log.d("VIEWHOLDER", currentUserUUID));
    }
    public void setCurrentUserUUID(String currentUserUUID){
        this.currentUserUUID = currentUserUUID;
    }
}
