package com.example.findworker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.findworker.helpers.FirebaseHelper;
import com.example.findworker.models.Worker;
import com.example.findworker.profile.UserProfile;

import static com.example.findworker.helpers.LoggedUserData.loggedUserEmail;
import static com.example.findworker.helpers.LoggedUserData.loggedUserName;
import static com.example.findworker.helpers.LoggedUserData.regiserUserUUID;

public class SelectRole extends AppCompatActivity {
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role);
        initializeSharedPreference();
    }

    public void workerUpdate(View view) {
        saveToSharedPreference(1);
//        Log.d("ROLE=","START");
//        Log.d("ROLE=","loggedUserEmail="+loggedUserEmail+"loggedUserName="+loggedUserName+"regiserUserUUID="+regiserUserUUID);
//        Worker worker = new Worker(loggedUserEmail,loggedUserName,"default");
//        FirebaseHelper.userDatabaseReference.child(regiserUserUUID).setValue(worker);
//        Log.d("ROLE=",worker.getEmail());
        startActivity(new Intent(getApplicationContext(),WorkerProfile.class));
    }

    public void userProfile(View view) {
        saveToSharedPreference(0);
        startActivity(new Intent(getApplicationContext(), UserProfile.class));
    }
    private void initializeSharedPreference(){
        prefs = getSharedPreferences("preference.txt",MODE_PRIVATE);
    }
    private void saveToSharedPreference(int userType){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("userType", String.valueOf(userType));
        editor.apply();
    }
}