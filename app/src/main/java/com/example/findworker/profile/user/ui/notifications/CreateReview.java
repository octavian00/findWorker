package com.example.findworker.profile.user.ui.notifications;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.findworker.FireBaseCallBack;
import com.example.findworker.R;
import com.example.findworker.helpers.FirebaseHelper;
import com.example.findworker.helpers.LoggedUserData;
import com.example.findworker.models.Review;
import com.example.findworker.models.User;
import com.example.findworker.models.UserReview;
import com.example.findworker.models.Worker;
import com.example.findworker.models.WorkerOrders;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateReview extends AppCompatActivity {
    RatingBar r;
    EditText shortDescription_edt, review_edt;
    Button submit_btn;
    String userUUID;
    final String TAG="CREATEREVIEW";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_review);
        initializeViews();
        listener();
    }
    private void initializeViews(){
        r=findViewById(R.id.rating_bar);
        shortDescription_edt = findViewById(R.id.edt_shortDescription);
        review_edt = findViewById(R.id.edt_user_review);
        submit_btn = findViewById(R.id.btn_submit_review);
        userUUID = getIntent().getStringExtra("UUID");
        Log.d("CREATEREVIEW",userUUID);
    }
    private void listener(){
        submit_btn.setOnClickListener(v -> updateWorker());
    }
    private Review createReview(){
        return  new Review(shortDescription_edt.getText().toString(),review_edt.getText().toString(),
                (double) r.getRating(), LoggedUserData.loggedUserEmail);
    }
    private void updateWorker(){
        if(LoggedUserData.currentWorker ==null){
            Log.d("CREATEREVIEW","null");
        }
        WorkerOrders worker = LoggedUserData.currentWorker;
        worker.addReview(createReview());
        populateWorkeReviewAverage(worker);
        Log.d("CREATEREVIEW email=",worker.getReviews().get(0).getUserEmail());
        FirebaseHelper.userDatabaseReference.child(userUUID).setValue(worker);
        updateUserListReviews();
    }
    private void populateWorkeReviewAverage(WorkerOrders workerOrders){
        if(workerOrders.getAverage() == null){
            workerOrders.setAverage((double) r.getRating());
            workerOrders.setNumberOfReviews(1);
        }else{
            workerOrders.setAverage(calculateAverage(workerOrders.getAverage(),workerOrders.getNumberOfReviews()));
            workerOrders.setNumberOfReviews(workerOrders.getNumberOfReviews()+1);
        }
    }
    private double calculateAverage(double currentAverage,Integer numberOfReviews){
        return (currentAverage*numberOfReviews+r.getRating())/++numberOfReviews;
    }
    private void getCurrenUser(FireBaseCallBack fireBaseCallBack){
        FirebaseHelper.userDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserReview userReview =
                        snapshot.child(LoggedUserData.regiserUserUUID).getValue(UserReview.class);
                fireBaseCallBack.onCallBackUserReview(userReview);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void updateUserListReviews(){
        getCurrenUser(new FireBaseCallBack() {
            @Override
            public void onCallBack(Worker worker) {

            }

            @Override
            public void onCallBackListOfWorkers(List<WorkerOrders> worker) {

            }

            @Override
            public void onCallBackListOfClients(Map<String, User> users) {

            }

            @Override
            public void onCallBackMapidEmails(Map<String, Worker> idAndEmails) {

            }

            @Override
            public void onCallBackUserReview(UserReview userReview) {
                ArrayList<String> usersReviews = (ArrayList<String>) userReview.getWorkersForReview();
                for(String s: usersReviews){
                    if(s.equals(userUUID)){
                        usersReviews.remove(s);
                        break;
                    }
                }
                userReview.setWorkersForReview(usersReviews);
                Log.d(TAG, String.valueOf(userReview.getWorkersForReview().size()));
                FirebaseHelper.userDatabaseReference.child(LoggedUserData.regiserUserUUID).setValue(userReview);
            }
        });
    }
}