package com.example.findworker.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.findworker.FireBaseCallBack;
import com.example.findworker.R;
import com.example.findworker.helpers.FirebaseHelper;
import com.example.findworker.helpers.LoggedUserData;
import com.example.findworker.models.Worker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserProfile extends AppCompatActivity {
    RecyclerView rv;
    private ListWorkersAdapter listWorkersAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoggedUserData.uuidlist = new ArrayList<>();
        setContentView(R.layout.activity_user_profile);
        rv = findViewById(R.id.rv_worker_list);
        getWorkers();
    }
    public void getAllWorkers(FireBaseCallBack fireBaseCallBack) {
        ArrayList<Worker> workers = new ArrayList<>();
        FirebaseHelper.userDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Worker user = dataSnapshot.getValue(Worker.class);
                    Log.d("UserProfile1=",user.showWorkersField());
                    if(user.getJobTitle()!=null && !user.getJobTitle().equals(" default")) {
                        workers.add(user);
                        LoggedUserData.uuidlist.add(dataSnapshot.getKey());
                    }
                }
                fireBaseCallBack.onCallBackListOfWorkers(workers);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void getWorkers(){
        getAllWorkers(new FireBaseCallBack() {
            @Override
            public void onCallBack(Worker worker) {

            }

            @Override
            public void onCallBackListOfWorkers(List<Worker> worker) {
                for(Worker w:worker){
                 Log.d("UserProfile=",w.showWorkersField());
                }
                if(worker.size() !=0 ) {
                    setRecyclerView(worker);
                }else {
                    Log.e("USERPROFILE","EMPTY");
                }
            }
        });
    }
    private void setRecyclerView(List<Worker> workerList){
        listWorkersAdapter = new ListWorkersAdapter(workerList);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv.setAdapter(listWorkersAdapter);
    }
}