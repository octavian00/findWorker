package com.example.findworker.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.icu.number.Precision;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

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
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DetaliedWorkerFromUser extends AppCompatActivity implements Serializable {
    private final static String TAG="DetaliedWorkerFromUser";
    private TextView tv_experience,tv_det_username,tv_det_location,tv_det_commomFriends,tv_det_average, tv_det_email;
    private Button btn_order;
    private RecyclerView rv;
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
        rv=findViewById(R.id.rv_det_reviews);
    }
    private void populateViews(WorkerOrders workerOrders, List<String> result){
        tv_experience.setText("Experience: "+workerOrders.getExperience());
        tv_det_email.setText("Email: "+workerOrders.getEmail());
        tv_det_username.setText("Username: "+workerOrders.getUsername());
        tv_det_location.setText("Location: "+workerOrders.getLocation());
        tv_det_average.setText("Rating: "+ df2.format(workerOrders.getAverage()));
        result = result.stream().distinct().collect(Collectors.toList());
        String json = new Gson().toJson(result);
        tv_det_commomFriends.setText("Friends who already collaborate: "+json);
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
                Log.d(TAG,"reviews size="+worker.getReviews().size());
                List<String> usernamesFB = worker.getReviews()
                                                 .stream().map(Review::getUsername)
                                                 .collect(Collectors.toList());
                List<String> facebookFriends=listFacebookFriends();
                if(facebookFriends ==null){
                    Log.e(TAG,"facebookFriends NULL");
                    return;
                }
                List<String> result = usernamesFB
                        .stream().filter(facebookFriends::contains)
                        .collect(Collectors.toList());
                populateViews(worker,result);
                setRecyclerView(worker.getReviews());
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
}