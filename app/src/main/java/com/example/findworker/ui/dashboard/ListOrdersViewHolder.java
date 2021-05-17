package com.example.findworker.ui.dashboard;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findworker.FireBaseCallBack;
import com.example.findworker.R;
import com.example.findworker.helpers.FirebaseHelper;
import com.example.findworker.helpers.LoggedUserData;
import com.example.findworker.models.UserReview;
import com.example.findworker.models.Worker;
import com.example.findworker.models.WorkerOrders;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ListOrdersViewHolder extends RecyclerView.ViewHolder {
    private TextView tv_userName;
    private Button btn_finish;
    private String currentUserUUID;
    private String username;
    int position;
    private final String TAG="LISTORDERS";
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
        btn_finish.setOnClickListener(v -> {
            Log.d(TAG,"START");
            Log.d(TAG,"USERNAME+"+username);
            FinishOrder finishOrder = new FinishOrder(currentUserUUID,position,username);
            finishOrder.execute();
        });
    }
    public void setCurrentUserUUID(String currentUserUUID){
        this.currentUserUUID = currentUserUUID;
    }
    public void setCurrentUserName(String userName ){
        this.username= userName;
    }
    public void setPosition(int posistion){
        this.position = posistion;
    }
}
