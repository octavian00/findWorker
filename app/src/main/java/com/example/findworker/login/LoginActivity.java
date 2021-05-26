package com.example.findworker.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.findworker.DeserializeJsonArray;
import com.example.findworker.FillData;
import com.example.findworker.FireBaseCallBack;
import com.example.findworker.R;
import com.example.findworker.SelectRole;
import com.example.findworker.WorkerProfile;
import com.example.findworker.helpers.FirebaseHelper;
import com.example.findworker.models.User;
import com.example.findworker.models.UserReview;
import com.example.findworker.models.Worker;
import com.example.findworker.models.WorkerOrders;
import com.example.findworker.profile.UserProfile;
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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.findworker.helpers.LoggedUserData.loggedUserName;
import static com.example.findworker.helpers.LoggedUserData.loggedUserEmail;
import static com.example.findworker.helpers.LoggedUserData.regiserUserUUID;

public class  LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private Button logInButton, createAccountButton;
    private EditText emailInput, passwordInput;
    private FirebaseAuth firebaseAuth;
    private FirebaseHelper firebaseHelper;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private  Map<String,Worker> currentUserIdAndEmail;
    final Worker[] w = {new Worker()};
    boolean isRoleActivity = false;
    SharedPreferences prefs;
    JSONArray jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeViews();
        initializeSharedPreference();
        getFromShared();
        automaticAuthUser();
        initializeFirebaseInstances();
        facebookRegister();
        emailsFromDb();
        Log.e("TAG","ZUZUZ");
    }
    private void emailsFromDb(){
        getAllEmailsFromDB(new FireBaseCallBack() {
            @Override
            public void onCallBack(WorkerOrders worker) {

            }

            @Override
            public void onCallBackListOfWorkers(List<WorkerOrders> worker) {

            }

            @Override
            public void onCallBackListOfClients(Map<String, User> users) {

            }

            @Override
            public void onCallBackMapidEmails(Map<String, Worker> idAndEmails) {
                currentUserIdAndEmail = idAndEmails;
            }

            @Override
            public void onCallBackUserReview(UserReview userReview) {

            }

            @Override
            public void onCallBackUser(User user) {

            }
        });
    }
    private void facebookRegister() {
        loginButton.setPermissions(Arrays.asList("email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG,"SUCCES");
                handleFacebookAccessToken(loginResult.getAccessToken());
            }
            @Override
            public void onCancel() {
                Log.d(TAG,"register CANCEL");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e(TAG,"register ERROR");
            }
        });
    }
    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful())
                        addUserToDB();
                    nextAct();
                });
    }
    private void initializeSharedPreference(){
        prefs =getSharedPreferences("preference.txt",MODE_PRIVATE);
    }
    private void saveToSharedPreference(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("UUID",regiserUserUUID);
        editor.putString("loggedUserName",loggedUserName);
        editor.putString("loggedUserEmail",loggedUserEmail);
        editor.apply();
    }
    private void getFromShared(){
        String data = prefs.getString("UUID","");
        String dataUsername = prefs.getString("loggedUserName","");
        String dataEmail = prefs.getString("loggedUserEmail","");
        if(data.isEmpty()){
            Log.d(TAG,"getFromShared is empty");
        }else{
            regiserUserUUID =data;
            Log.d(TAG,"Found UUID :)");
        }
        if(!dataEmail.isEmpty())
            loggedUserEmail = dataEmail;
        if(!dataUsername.isEmpty())
            loggedUserName = dataUsername;
    }
    private void automaticAuthUser(){
        String userType = getUserTypeFromShared();
        if(userType.isEmpty()){
            return;
        }
        if(Integer.parseInt(userType)== 0){
            nextActivity(UserProfile.class);
            return;
        }
        if(Integer.parseInt(userType) == 1){
            nextActivity(WorkerProfile.class);
        }
    }
    private String getUserTypeFromShared(){
        return prefs.getString("userType","");
    }


    private void addUserToDB() {
        Log.d(TAG,"addUserToDB");
        FirebaseUser user = firebaseAuth.getCurrentUser();
        populateConstants(user);
        saveToSharedPreference();
        Log.d(TAG,"username "+user.getDisplayName()+" "+user.getEmail()+"UUID"+user.getUid());
        for(Map.Entry<String, Worker> s:currentUserIdAndEmail.entrySet()){
           if(user.getEmail().equals(s.getValue().getEmail())){
               Log.d(TAG,"email Already exists");
               regiserUserUUID = s.getKey();
                Log.d(TAG,"regiserUserUUID"+ regiserUserUUID);
               return;
           }
        }

        User registeredUser = new User(loggedUserEmail,loggedUserName);
        Log.d(TAG,"loggedUserEmail="+loggedUserEmail+"loggedUserName="+loggedUserName+"regiserUserUUID="+regiserUserUUID);
        firebaseHelper.userDatabaseReference.child(regiserUserUUID).setValue(registeredUser);
    }
    private void populateConstants(FirebaseUser user){
        loggedUserName = user.getDisplayName();
        loggedUserEmail = user.getEmail();
        regiserUserUUID = user.getUid();
    }
    private void getAllEmailsFromDB(FireBaseCallBack fireBaseCallBack){
        currentUserIdAndEmail = new HashMap<>();
        FirebaseHelper.userDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                     Worker user = dataSnapshot.getValue(Worker.class);
                     currentUserIdAndEmail.put(dataSnapshot.getKey(),user);
                     fireBaseCallBack.onCallBackMapidEmails(currentUserIdAndEmail);
                 }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initializeViews(){
       // LoggedUserData.userNameList = new ArrayList<>();
        callbackManager = CallbackManager.Factory.create();
        EditText emailInput;
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
        Log.d(TAG,"onResult");
        GraphRequest graphRequest = GraphRequest.newMyFriendsRequest(AccessToken.getCurrentAccessToken(),((objects, response) -> {
            Log.d("FRIENDS",objects.toString());
            jsonObject = objects;
            saveFacebookFriendsToShared();
        }));
        graphRequest.executeAsync();
    }
    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            Log.d(TAG,"ACCES TOKEN");
            if(currentAccessToken == null){
                LoginManager.getInstance().logOut();
                logOut();
            }
        }
    };
    private void logOut(){
        SharedPreferences.Editor sharedPreferences =
                getSharedPreferences("preference.txt", MODE_PRIVATE).edit();
        sharedPreferences.clear().apply();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    private void saveFacebookFriendsToShared(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("facebookFriends",jsonObject.toString());
        editor.apply();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDESTROY");
        accessTokenTracker.stopTracking();
    }

    private void initializeFirebaseInstances(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseHelper = FirebaseHelper.getInstance();
    }

    public void logInActivity(View view) {
        final String email = emailInput.getText().toString();
        final String password = passwordInput.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //LoggedUserData.loggedUserPassword = password;
                            Toast.makeText(getBaseContext(), "success", Toast.LENGTH_SHORT).show();
                            //nextActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getBaseContext(), "Something went wrong :(", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    private void nextActivity(Class MyClass){
        Intent intent = new Intent(this, MyClass);
        startActivity(intent);
    }

    public void registerActivity(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    private void nextAct(){
        String userType = getUserTypeFromShared();
        if(userType.isEmpty()){
            nextActivity(SelectRole.class);
            return;
        }
        if(Integer.parseInt(userType)== 0){
            nextActivity(UserProfile.class);
            return;
        }
        if(Integer.parseInt(userType) == 1){
            nextActivity(WorkerProfile.class);
        }
    }
}