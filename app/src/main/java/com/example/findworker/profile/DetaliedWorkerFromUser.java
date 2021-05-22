package com.example.findworker.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findworker.DeserializeJsonArray;
import com.example.findworker.FireBaseCallBack;
import com.example.findworker.R;
import com.example.findworker.helpers.FirebaseHelper;
import com.example.findworker.helpers.LoggedUserData;
import com.example.findworker.models.Review;
import com.example.findworker.models.User;
import com.example.findworker.models.UserReview;
import com.example.findworker.models.Worker;
import com.example.findworker.models.WorkerOrders;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class  DetaliedWorkerFromUser extends AppCompatActivity implements Serializable {
    private final static String TAG="DetaliedWorkerFromUser";
    private TextView tv_experience,tv_det_username,tv_det_location,tv_det_commomFriends,tv_det_average, tv_det_email;
    private Button btn_order,btn_call;
    private RecyclerView rv;
    private static final int REQUEST_CALL = 1;
    String userUUID;
    SharedPreferences sharedPreferences;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalied_worker_from_user);
        getData();
        initializeViews();
        listener();
        initializeSharedPreference();
        listFacebookFriends();
        differenceBetweenLists();

    }
    private void initializeSharedPreference(){
        sharedPreferences = getSharedPreferences("preference.txt",MODE_PRIVATE);
    }
    private String getUserFriendsFromShared(){
        return sharedPreferences.getString("facebookFriends","");
    }
    private List<String> listFacebookFriends(){
        List<String> facebookFriendsList = new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(getUserFriendsFromShared());
            DeserializeJsonArray deserializeJsonArray = new DeserializeJsonArray(jsonArray);
            facebookFriendsList = deserializeJsonArray.convertToStringList();
            for(String friend:facebookFriendsList){
                Log.d(TAG,"friends="+friend);
            }
        }catch (JSONException jsonException){
            jsonException.printStackTrace();
        }
        return facebookFriendsList;
    }
    private void getData(){
        userUUID = getIntent().getStringExtra("UUID");
        Log.d(TAG,LoggedUserData.currentWorker.getEmail());
        Log.d(TAG,"registerUUID="+LoggedUserData.regiserUserUUID);
        Log.d(TAG,"userUUID="+userUUID);
    }
    private void initializeViews(){
        tv_experience = findViewById(R.id.tv_det_experiencee);
        tv_det_average = findViewById(R.id.tv_det_average);
        tv_det_commomFriends = findViewById(R.id.tv_det_commomFriends);
        tv_det_location = findViewById(R.id.tv_det_location);
        tv_det_username = findViewById(R.id.tv_det_username);
        tv_det_email = findViewById(R.id.tv_det_email);
        btn_order = findViewById(R.id.btn_order);
        btn_call = findViewById(R.id.btn_call);
        rv=findViewById(R.id.rv_det_reviews);
    }
    private void populateCommonFrieds(List<String> result){
        result = result.stream().distinct().collect(Collectors.toList());
        String json = new Gson().toJson(result);
        tv_det_commomFriends.setText("Friends who already collaborate: "+json);
    }

    private void populateBasicViews(WorkerOrders workerOrders) {
        if(workerOrders.getExperience() !=null){
            tv_experience.setText("Experience: "+workerOrders.getExperience());
        }
        if(workerOrders.getEmail() !=null){
            tv_det_email.setText("Email: "+workerOrders.getEmail());
        }
        if(workerOrders.getUsername() !=null){
            tv_det_username.setText("Username: "+workerOrders.getUsername());
        }
        if(workerOrders.getLocation() !=null){
            tv_det_location.setText("Location: "+workerOrders.getLocation());
        }
        if(workerOrders.getAverage() !=null) {
            tv_det_average.setText("Rating: " + df2.format(workerOrders.getAverage()));
        }
    }

    private void setRecyclerView(List<Review> reviewList){
        if(reviewList !=null) {
            ListReviewAdapter listReviewAdapter = new ListReviewAdapter(reviewList);
            rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            rv.setAdapter(listReviewAdapter);
        }
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
            finishAndRemoveTask();
        });
        btn_call.setOnClickListener(v ->
            {makePhoneCall(LoggedUserData.currentWorker.getPhoneNumber());});

    }
    private void getListOfReviews(FireBaseCallBack fireBaseCallBack){
        FirebaseHelper.userDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                WorkerOrders workerOrders = snapshot.child(userUUID).getValue(WorkerOrders.class);
                fireBaseCallBack.onCallBack(workerOrders);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void differenceBetweenLists(){
        getListOfReviews(new FireBaseCallBack() {
            @Override
            public void onCallBack(WorkerOrders worker) {
                populateBasicViews(worker);
                if(worker.getReviews() != null) {
                    Log.d(TAG, "reviews size=" + worker.getReviews().size());
                    List<String> usernamesFB = worker.getReviews()
                            .stream().map(Review::getUsername)
                            .collect(Collectors.toList());
                    List<String> facebookFriends = listFacebookFriends();
                    if (facebookFriends == null) {
                        Log.e(TAG, "facebookFriends NULL");
                        return;
                    }
                    List<String> result = usernamesFB
                            .stream().filter(facebookFriends::contains)
                            .collect(Collectors.toList());
                    populateCommonFrieds(result);
                    setRecyclerView(worker.getReviews());
                }
            }

            @Override
            public void onCallBackListOfWorkers(List<WorkerOrders> worker) {

            }

            @Override
            public void onCallBackListOfClients(Map<String, User> users) {

            }

            @Override
            public void onCallBackMapidEmails(Map<String, Worker> idAndEmails) {

            }

            @Override
            public void onCallBackUserReview(UserReview userReview) {

            }
        });
    }

    private void makePhoneCall(String number){
        if(number.trim().length() > 0){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(DetaliedWorkerFromUser.this,
                        new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            }else{
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall(LoggedUserData.currentWorker.getPhoneNumber());
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}