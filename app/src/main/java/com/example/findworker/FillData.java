package com.example.findworker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.findworker.helpers.FirebaseHelper;
import com.example.findworker.models.Worker;

import static com.example.findworker.helpers.LoggedUserData.loggedUserEmail;
import static com.example.findworker.helpers.LoggedUserData.loggedUserName;
import static com.example.findworker.helpers.LoggedUserData.regiserUserUUID;

public class FillData extends AppCompatActivity {
    Button btn_submit;
    EditText edt_jobtTitle,edt_experience,edt_location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_data);
        initializeData();
        listener();
    }
    //next step validate data
    private void initializeData(){
        btn_submit =findViewById(R.id.btn_submit_data);
        edt_jobtTitle = findViewById(R.id.edt_jobTitle);
        edt_experience = findViewById(R.id.edt_experience);
        edt_location = findViewById(R.id.edt_location);
    }
    private void listener(){
        btn_submit.setOnClickListener(v -> {
            Log.d("FILLDATA","START");
            Worker worker =new Worker(loggedUserEmail,loggedUserName,edt_jobtTitle.getText().toString(),Integer.valueOf(edt_experience.getText().toString()),edt_location.getText().toString());
            Log.d("FILLDATA","regiserUserUUID="+regiserUserUUID);
            Log.d("FILLDATA","getJobTitle="+worker.getJobTitle());
            Log.d("FILLDATA","getExperience="+worker.getExperience());
            Log.d("FILLDATA","getLocation="+worker.getLocation());
            FirebaseHelper.userDatabaseReference.child(regiserUserUUID).setValue(worker);
        });
    }
}