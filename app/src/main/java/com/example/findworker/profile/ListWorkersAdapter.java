package com.example.findworker.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findworker.R;
import com.example.findworker.helpers.LoggedUserData;
import com.example.findworker.models.Worker;
import com.example.findworker.models.WorkerOrders;

import java.io.Serializable;
import java.util.List;

public class ListWorkersAdapter extends RecyclerView.Adapter<ListWorkersViewHolder> implements Serializable {
    private Context context;
    private List<WorkerOrders> workerList;
    public  ListWorkersAdapter(List<WorkerOrders> workers){
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
        Log.d("UUID=", LoggedUserData.uuidlist.get(position));
        Log.d("EMAIL=", worker.getEmail());
        holder.itemView.setOnClickListener(v -> {
            Log.d("EMAIL=", worker.getEmail());
            Intent intent = new Intent(context,DetaliedWorkerFromUser.class);
            intent.putExtra("UUID",LoggedUserData.uuidlist.get(position));
            LoggedUserData.currentWorker = worker;
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return workerList.size();
    }
}
