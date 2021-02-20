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
import com.example.findworker.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseHelper firebaseHelper;

    private EditText emailInput, passwordInput;

    Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeViews();
        initializeFirebaseInstances();
    }
    private void initializeViews(){
        emailInput = findViewById(R.id.emailRegInput);
        passwordInput = findViewById(R.id.passwordRegInput);
        createAccountButton = findViewById(R.id.createAccountButton);
    }
    private void initializeFirebaseInstances(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseHelper = FirebaseHelper.getInstance();
    }

    public void createAccount(View view){
        final String email = emailInput.getText().toString();
        final String password = passwordInput.getText().toString();

//        if(!inputCheck(userName,email,password)){
//            return;
//
//        }
//
//        if(LoggedUserData.userNameList.contains(userName)){
//            Toast.makeText(getBaseContext(), existUserNameToast, Toast.LENGTH_SHORT).show();
//            return;
//
//        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            User registeredUser = new User(email,password);
                            firebaseHelper.userDatabaseReference.child(UUID.randomUUID().toString()).setValue(registeredUser);
                            Toast.makeText(getBaseContext(), "success", Toast.LENGTH_SHORT).show();
//                            LoggedUserData.loggedUserPassword = password;
//                            LoggedUserData.loggedUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
//                            updateMillis();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finishAndRemoveTask();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getBaseContext(), "Something went wrong :(", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    public void hasAnAccount(View view) {
        finishAndRemoveTask();
    }
}