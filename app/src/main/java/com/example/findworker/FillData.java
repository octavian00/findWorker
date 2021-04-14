package com.example.findworker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.findworker.helpers.FirebaseHelper;

import static com.example.findworker.helpers.LoggedUserData.regiserUserUUID;

public class FillData extends AppCompatActivity {
    Button btn_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_data);
        initializeData();
        listener();
    }
    private void initializeData(){
        btn_submit =findViewById(R.id.btn_submit);
    }
    private void listener(){
        btn_submit.setOnClickListener(v -> {
            FirebaseHelper.userDatabaseReference.child(regiserUserUUID).setValue(worker);
        });
    }
}