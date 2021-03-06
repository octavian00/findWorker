package com.example.findworker.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.findworker.MainActivity;
import com.example.findworker.R;
import com.example.findworker.helpers.FirebaseHelper;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;



public class LoginActivity extends AppCompatActivity {
    private Button logInButton, createAccountButton;
    private EditText emailInput, passwordInput;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser loggedUser;
    private FirebaseHelper firebaseHelper;
    private CallbackManager callbackManager;
    private LoginButton loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeViews();
        initializeFirebaseInstances();
        facebookRegister();
    }

    private void facebookRegister() {
        // loginButton.setPermissions(Arrays.asList("user_friends"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("ZUZU register","SUCCESS");
            }

            @Override
            public void onCancel() {
                Log.d("ZUZU register","CANCEL");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("ZUZU register","ERROR");
            }
        });
    }

    private void initializeViews(){
       // LoggedUserData.userNameList = new ArrayList<>();
        callbackManager = CallbackManager.Factory.create();
        emailInput = findViewById(R.id.emailLogInput);
        passwordInput = findViewById(R.id.passwordLogInput);

        logInButton = findViewById(R.id.logInButton);
        createAccountButton = findViewById(R.id.createAccountButton);
        loginButton = findViewById(R.id.login_button);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // for read/write facebook user data
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), (object, response) -> {
            Log.d("ZUZU graphRequest",object.toString());
            //deserialize Json response
            try {
                String name = object.getString("name");
                emailInput.setText(name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        Bundle bundle = new Bundle();
        bundle.putString("fields","gender, name, id, first_name, last_name");//this fields comes from facebook devolopers
        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
    }
    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if(currentAccessToken == null){
                LoginManager.getInstance().logOut();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
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