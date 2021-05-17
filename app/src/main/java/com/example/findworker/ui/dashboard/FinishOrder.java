package com.example.findworker.ui.dashboard;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.findworker.FireBaseCallBack;
import com.example.findworker.helpers.FirebaseHelper;
import com.example.findworker.helpers.LoggedUserData;
import com.example.findworker.models.User;
import com.example.findworker.models.UserReview;
import com.example.findworker.models.WorkerOrders;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class FinishOrder {
    private String currentserUUID;
    private final String TAG="FINISHORDER";
    String currentUsername;
    private int position;
    public FinishOrder(String current, int position,String currentUsername) {
        this.currentserUUID = current;
        this.position = position;
        this.currentUsername = currentUsername;
        Log.d(TAG,"FINISH ORDER constructed");

    }
    public void execute(){
        updateUser();
    }

    private void updateUser() {
        Log.d(TAG,"updateUser 0 ");
        FirebaseHelper.userDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG,"updateUser  1="+ LoggedUserData.regiserUserUUID);
                Log.d(TAG,"updateUser  2="+ currentserUUID);
                UserReview userReview = snapshot.child(currentserUUID).getValue(UserReview.class);
                if(userReview==null) {
                    Log.e(TAG, "user not found");

                }else {
                    Log.d(TAG, "reviews=" + userReview.getEmail()) ;
                    if(!userReview.isAlreadyAdded(LoggedUserData.regiserUserUUID)) { // to avoid delete all list -> this can be improved
                        userReview.addWorkerForReview(LoggedUserData.regiserUserUUID);
                        Log.d(TAG, "reviews1=" + userReview.getWorkersForReview().size());
                        FirebaseHelper.userDatabaseReference.child(currentserUUID).setValue(userReview);
                        WorkerOrders w = snapshot.child(LoggedUserData.regiserUserUUID).getValue(WorkerOrders.class);
                        w.removePendingOrders(position);
                        w.addFinishOrder(currentUsername);
                        FirebaseHelper.userDatabaseReference.child(LoggedUserData.regiserUserUUID).setValue(w);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
