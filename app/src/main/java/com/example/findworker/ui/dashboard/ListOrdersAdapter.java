package com.example.findworker.ui.dashboard;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findworker.R;
import com.example.findworker.helpers.LoggedUserData;
import com.example.findworker.models.User;
import com.example.findworker.models.WorkerOrders;

import java.util.List;
import java.util.Map;


public class ListOrdersAdapter extends RecyclerView.Adapter<ListOrdersViewHolder> {
    private Context context;
    private Map<String,User> userList;

    public ListOrdersAdapter(Map<String,User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public ListOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View contactView = layoutInflater.inflate(R.layout.orders_row,parent,false);
        return  new ListOrdersViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListOrdersViewHolder holder, int position) {
        final User user = (User) userList.values().toArray()[position];
        holder.setValues(user.getEmail());
        holder.setCurrentUserUUID((String) userList.keySet().toArray()[position]);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
