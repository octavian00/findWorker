package com.example.findworker.ui.dashboard;

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
import com.example.findworker.models.Order;
import com.example.findworker.models.User;
import com.example.findworker.models.UserReview;
import com.example.findworker.models.Worker;
import com.example.findworker.models.WorkerOrders;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private final String TAG="DASHBOARD_FRAGMENT";
    Map<String ,User> users;
    private ListOrdersAdapter listOrdersAdapter;
    RecyclerView rv;
    WorkerOrders currentWorker;
    FirebaseHelper firebaseHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_worker_view_orders, container, false);
        initializeViews(root);
        pendingOrdersCallback();
        return root;
    }
    private void initializeViews(View root){
      rv = root.findViewById(R.id.rv_show_orders);
      firebaseHelper = FirebaseHelper.getInstance();
    }
    private void getPendinOrders(FireBaseCallBack fireBaseCallBack){
        users =new HashMap<>();
        FirebaseHelper.userDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                Log.d(TAG,LoggedUserData.regiserUserUUID);
                currentWorker = snapshot.child(LoggedUserData.regiserUserUUID).getValue(WorkerOrders.class);
                if(currentWorker != null && currentWorker.getPendingOrders()!=null) {
                    for(Order currentUserUUID:currentWorker.getPendingOrders()) {
                        Log.d(TAG, "currentUUID" + currentUserUUID);
                        User currentUser = snapshot.child(currentUserUUID.getUserUUID()).getValue(User.class);
                        if (currentUser != null) {
                            users.put(currentUserUUID.getUserUUID(),currentUser);
                        } else {
                            Log.d(TAG, "NOT USER");
                        }
                    }
                    fireBaseCallBack.onCallBackListOfClients(users);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
       setRecyclerView();
    }
    private void setRecyclerView(){
        if(users.size() == 0) {
            Log.d(TAG, "recyclerv SIZE LIST=" + users.size());
            return;
        }
        listOrdersAdapter = new ListOrdersAdapter(users);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(listOrdersAdapter);
    }
    private void pendingOrdersCallback(){
        getPendinOrders(new FireBaseCallBack() {
            @Override
            public void onCallBack(WorkerOrders worker) {

            }

            @Override
            public void onCallBackListOfWorkers(List<WorkerOrders> worker) {

            }

            @Override
            public void onCallBackListOfClients(Map<String,User> userList) {
                users=userList;
                setRecyclerView();
            }

            @Override
            public void onCallBackMapidEmails(Map<String, Worker> idAndEmails) {

            }

            @Override
            public void onCallBackUserReview(UserReview userReview) {

            }

            @Override
            public void onCallBackUser(User user) {

            }
        });
    }
}