package com.example.findworker.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.findworker.FireBaseCallBack;
import com.example.findworker.R;
import com.example.findworker.helpers.FirebaseHelper;
import com.example.findworker.helpers.LoggedUserData;
import com.example.findworker.models.User;
import com.example.findworker.models.Worker;
import com.example.findworker.models.WorkerOrders;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserProfile extends AppCompatActivity {
//    RecyclerView rv;
//    private ListWorkersAdapter listWorkersAdapter;
//    private FirebaseHelper firebaseHelper;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        LoggedUserData.uuidlist = new ArrayList<>();
//        firebaseHelper = FirebaseHelper.getInstance();
//        setContentView(R.layout.activity_user_profile);
//        rv = findViewById(R.id.rv_worker_list);
//        getWorkers();
//    }
//    public void getAllWorkers(FireBaseCallBack fireBaseCallBack) {
//        ArrayList<WorkerOrders> workers = new ArrayList<>();
//        firebaseHelper.userDatabaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
//                    WorkerOrders user = dataSnapshot.getValue(WorkerOrders.class);
//                    Log.d("UserProfile1=",user.showWorkersField());
//                    if(user.getJobTitle()!=null && !user.getJobTitle().equals(" default")) {
//                        workers.add(user);
//                        LoggedUserData.uuidlist.add(dataSnapshot.getKey());
//                    }
//                }
//                fireBaseCallBack.onCallBackListOfWorkers(workers);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//    }
//    private void getWorkers(){
//        getAllWorkers(new FireBaseCallBack() {
//            @Override
//            public void onCallBack(Worker worker) {
//
//            }
//
//            @Override
//            public void onCallBackListOfWorkers(List<WorkerOrders> worker) {
//                for(WorkerOrders w:worker){
//                 Log.d("UserProfile=",w.showWorkersField());
//                }
//                if(worker.size() !=0 ) {
//                    setRecyclerView(worker);
//                }else {
//                    Log.e("USERPROFILE","EMPTY");
//                }
//            }
//
//            @Override
//            public void onCallBackListOfClients(Map<String,User> users) {
//
//            }
//
//            @Override
//            public void onCallBackMapidEmails(Map<String, Worker> idAndEmails) {
//
//            }
//        });
//    }
//    private void setRecyclerView(List<WorkerOrders> workerList){
//        listWorkersAdapter = new ListWorkersAdapter(workerList);
//        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        rv.setAdapter(listWorkersAdapter);
//    }
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_profile);
    BottomNavigationView navView = findViewById(R.id.nav_view_user);
//    // Passing each menu ID as a set of Ids because each
//    // menu should be considered as top level destinations.
    AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
            R.id.user_home, R.id.user_dashboard, R.id.user_notifications)
            .build();
     NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_user);
     NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
     NavigationUI.setupWithNavController(navView, navController);
}
}