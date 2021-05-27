package com.example.findworker.profile.user.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findworker.R;
import com.example.findworker.helpers.FirebaseHelper;
import com.example.findworker.helpers.LoggedUserData;
import com.example.findworker.models.UserReview;
import com.example.findworker.models.WorkerOrders;
import com.example.findworker.profile.ListWorkerForReviewAdapter;
import com.example.findworker.profile.ListWorkersAdapter;
import com.example.findworker.ui.dashboard.ListOrdersAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {
    RecyclerView rv;
    List<WorkerOrders> workersForReview;
    TextView tv_leaveReview;
    private ListWorkerForReviewAdapter listWorkersAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_review, container, false);
        rv=root.findViewById(R.id.rv_worker_review);
        tv_leaveReview = root.findViewById(R.id.tv_leaveReview);
        LoggedUserData.uuidlist = new ArrayList<>();
        return root;
    }
    private void getWorkersForReview(){
        workersForReview = new ArrayList<>();
        FirebaseHelper.getInstance();
        FirebaseHelper.userDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                workersForReview = new ArrayList<>();
                if(LoggedUserData.regiserUserUUID !=null) {
                    UserReview userReview = snapshot.child(LoggedUserData.regiserUserUUID).getValue(UserReview.class);
                    if (userReview.getWorkersForReview() != null) {
                        for (String s : userReview.getWorkersForReview()) {
                            workersForReview.add(snapshot.child(s).getValue(WorkerOrders.class));
                            LoggedUserData.uuidlist.add(snapshot.child(s).getKey());
                        }
                        for (WorkerOrders w : workersForReview) {
                            Log.d("WORKERS", w.getEmail());
                        }
                       // tv_leaveReview.setText("Nothing for review");
                        setRecyclerView(workersForReview);
                    }else{
                        tv_leaveReview.setText("Nothing for review");
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void setRecyclerView(List<WorkerOrders> workersForReview){
        if(workersForReview !=null) {
            listWorkersAdapter = new ListWorkerForReviewAdapter(workersForReview);
            rv.setLayoutManager(new LinearLayoutManager(getContext()));
            rv.setAdapter(listWorkersAdapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getWorkersForReview();
    }
}