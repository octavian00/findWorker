package com.example.findworker.profile;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findworker.R;
import com.example.findworker.helpers.LoggedUserData;
import com.example.findworker.models.WorkerOrders;
import com.example.findworker.profile.user.ui.notifications.CreateReview;

import java.util.List;

public class ListWorkerForReviewAdapter extends RecyclerView.Adapter<ListWorkersViewHolder> {
    private List<WorkerOrders> workerList;
    private Context context;
    public  ListWorkerForReviewAdapter(List<WorkerOrders> workers){
        this.workerList = workers;
    }
    @NonNull
    @Override
    public ListWorkersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View contactView = layoutInflater.inflate(R.layout.worker_row,parent,false);
        return  new ListWorkersViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListWorkersViewHolder holder, int position) {
        final WorkerOrders worker = workerList.get(position);
        holder.setValues(worker.getUsername(),worker.getJobTitle());
        holder.itemView.setOnClickListener(v -> {
            Log.d("EMAIL=", worker.getEmail());
            Log.d("uuidList",LoggedUserData.uuidlist.get(position)+"");
            Intent intent = new Intent(context, CreateReview.class);
            intent.putExtra("UUID", LoggedUserData.uuidlist.get(position));
            LoggedUserData.currentWorker = worker;
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return workerList.size();
    }
}
