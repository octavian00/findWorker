package com.example.findworker.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.findworker.R;
import com.example.findworker.helpers.FirebaseHelper;
import com.example.findworker.helpers.LoggedUserData;
import com.example.findworker.models.WorkerOrders;

import java.io.Serializable;
import java.util.ArrayList;

public class DetaliedWorkerFromUser extends AppCompatActivity implements Serializable {
    private final static String TAG="DetaliedWorkerFromUser";
    private TextView tv_experience;
    private Button btn_order;
    String userUUID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalied_worker_from_user);
        getData();
        initializeViews();
        listener();

    }
    private void getData(){
        userUUID = getIntent().getStringExtra("UUID");
        Log.d(TAG,LoggedUserData.currentWorker.getEmail());
        Log.d(TAG,"registerUUID="+LoggedUserData.regiserUserUUID);
        Log.d(TAG,"userUUID="+userUUID);
    }
    private void initializeViews(){
        tv_experience = findViewById(R.id.tv_experiencee);
        tv_experience.setText(LoggedUserData.currentWorker.getExperience().toString());
        btn_order = findViewById(R.id.btn_order);
    }
    private void listener(){
        btn_order.setOnClickListener(v -> {

            if(LoggedUserData.currentWorker.getPendingOrders() == null)
            {
                ArrayList<String> newOrder= new ArrayList<>();
                newOrder.add(LoggedUserData.regiserUserUUID);
                LoggedUserData.currentWorker.setPendingOrders(newOrder);
            }
            else{
                LoggedUserData.currentWorker.addUserOrder(LoggedUserData.regiserUserUUID);
            }
            WorkerOrders worker = LoggedUserData.currentWorker;
            FirebaseHelper.userDatabaseReference.child(userUUID).setValue(worker);
        });
    }
}