package com.example.findworker.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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
import com.example.findworker.models.Review;
import com.example.findworker.models.WorkerOrders;
import com.example.findworker.profile.ListReviewAdapter;
import com.example.findworker.profile.ListWorkerForReviewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class NotificationsFragment extends Fragment {

    private TextView tv_average;
    private RecyclerView rv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_worker_notifications, container, false);
        tv_average = root.findViewById(R.id.tv_worker_average);
        rv=root.findViewById(R.id.rv_worker_reviews);
        getUserFromDb();
        return root;
    }
    private void getUserFromDb(){
        FirebaseHelper.getInstance();
        FirebaseHelper.userDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                WorkerOrders currentWorker = snapshot.child(LoggedUserData.regiserUserUUID).getValue(WorkerOrders.class);
                if(currentWorker!=null) {
                    populateViews(currentWorker);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void populateViews(WorkerOrders currentWorker) {
        List<Review> reviewList = currentWorker.getReviews();
        if(currentWorker.getAverage() == null){
            return;
        }
        tv_average.setText(currentWorker.getAverage().toString());
        if(reviewList !=null) {
            ListReviewAdapter listReviewAdapter = new ListReviewAdapter(reviewList);
            rv.setLayoutManager(new LinearLayoutManager(getContext()));
            rv.setAdapter(listReviewAdapter);
        }
    }
}