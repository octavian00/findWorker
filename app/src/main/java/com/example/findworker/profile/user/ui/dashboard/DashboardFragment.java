package com.example.findworker.profile.user.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findworker.FireBaseCallBack;
import com.example.findworker.R;
import com.example.findworker.helpers.FirebaseHelper;
import com.example.findworker.helpers.LoggedUserData;
import com.example.findworker.models.User;
import com.example.findworker.models.UserReview;
import com.example.findworker.models.Worker;
import com.example.findworker.models.WorkerOrders;
import com.example.findworker.profile.ListWorkersAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DashboardFragment extends Fragment {

    RecyclerView rv;
    private ListWorkersAdapter listWorkersAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        LoggedUserData.uuidlist = new ArrayList<>();
        rv = root.findViewById(R.id.rv_worker_list);
        getWorkers();
        return root;
    }
    public void getAllWorkers(FireBaseCallBack fireBaseCallBack) {
        ArrayList<WorkerOrders> workers = new ArrayList<>();
        FirebaseHelper.userDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    WorkerOrders user = dataSnapshot.getValue(WorkerOrders.class);
                    Log.d("UserProfile1=",user.showWorkersField());
                    if(user.getJobTitle()!=null && !user.getJobTitle().equals(" default")) {
                        workers.add(user);
                        LoggedUserData.uuidlist.add(dataSnapshot.getKey());
                    }
                }
                fireBaseCallBack.onCallBackListOfWorkers(workers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
    private void getWorkers(){
        getAllWorkers(new FireBaseCallBack() {
            @Override
            public void onCallBack(Worker worker) {}
            @Override
            public void onCallBackListOfWorkers(List<WorkerOrders> worker) {
                for(WorkerOrders w:worker){
                    Log.d("UserProfile=",w.showWorkersField());
                }
                if(worker.size() !=0 ) {
                    setRecyclerView(worker);
                }else {
                    Log.e("USERPROFILE","EMPTY");
                }
            }

            @Override
            public void onCallBackListOfClients(Map<String, User> users) { }

            @Override
            public void onCallBackMapidEmails(Map<String, Worker> idAndEmails) {

            }

            @Override
            public void onCallBackUserReview(UserReview userReview) {

            }
        });
    }
    private void setRecyclerView(List<WorkerOrders> workerList){
        listWorkersAdapter = new ListWorkersAdapter(workerList);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(listWorkersAdapter);
    }
}