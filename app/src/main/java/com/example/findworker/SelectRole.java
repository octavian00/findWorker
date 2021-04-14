package com.example.findworker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.findworker.helpers.FirebaseHelper;
import com.example.findworker.models.Worker;

import static com.example.findworker.helpers.LoggedUserData.loggedUserEmail;
import static com.example.findworker.helpers.LoggedUserData.loggedUserName;
import static com.example.findworker.helpers.LoggedUserData.regiserUserUUID;

public class SelectRole extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role);
        Log.d("ROLE=","START Role");
    }

    public void workerUpdate(View view) {
        Log.d("ROLE=","START");
        Log.d("ROLE=","loggedUserEmail="+loggedUserEmail+"loggedUserName="+loggedUserName+"regiserUserUUID="+regiserUserUUID);
        Worker worker = new Worker(loggedUserEmail,loggedUserName,"default");
        FirebaseHelper.userDatabaseReference.child(regiserUserUUID).setValue(worker);
        Log.d("ROLE=",worker.getEmail());
        startActivity(new Intent(getApplicationContext(),FillData.class));
    }
}