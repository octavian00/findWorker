package com.example.findworker.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.findworker.MainActivity;
import com.example.findworker.R;
import com.example.findworker.helpers.FirebaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private Button logInButton, createAccountButton;
    private EditText emailInput, passwordInput;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser loggedUser;
    private FirebaseHelper firebaseHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeViews();
        initializeFirebaseInstances();
    }

    private void initializeViews(){
       // LoggedUserData.userNameList = new ArrayList<>();
        emailInput = findViewById(R.id.emailLogInput);
        passwordInput = findViewById(R.id.passwordLogInput);

        logInButton = findViewById(R.id.logInButton);
        createAccountButton = findViewById(R.id.createAccountButton);


    }

    private void initializeFirebaseInstances(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseHelper = FirebaseHelper.getInstance();
    }

    public void logInActivity(View view) {
        final String email = emailInput.getText().toString();
        final String password = passwordInput.getText().toString();

//        if(!inputCheck(email,password)){
//            return;
//
//        }
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //LoggedUserData.loggedUserPassword = password;
                            Toast.makeText(getBaseContext(), "success", Toast.LENGTH_SHORT).show();
                            nextActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getBaseContext(), "Something went wrong :(", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }
    private void nextActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void registerActivity(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}