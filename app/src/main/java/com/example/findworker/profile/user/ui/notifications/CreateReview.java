package com.example.findworker.profile.user.ui.notifications;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.findworker.R;
import com.example.findworker.helpers.FirebaseHelper;
import com.example.findworker.helpers.LoggedUserData;
import com.example.findworker.models.Review;
import com.example.findworker.models.Worker;
import com.example.findworker.models.WorkerOrders;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class CreateReview extends AppCompatActivity {
    RatingBar r;
    EditText shortDescription_edt, review_edt;
    Button submit_btn;
    String userUUID;
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
        Log.d("CREATEREVIEW email=",worker.getReviews().get(0).getUserEmail());
        FirebaseHelper.userDatabaseReference.child(userUUID).setValue(worker);
    }
}