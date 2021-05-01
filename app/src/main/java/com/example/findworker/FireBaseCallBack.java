package com.example.findworker;

import com.example.findworker.models.User;
import com.example.findworker.models.Worker;
import com.example.findworker.models.WorkerOrders;

import java.util.List;
import java.util.Map;

public interface FireBaseCallBack {
    void onCallBack(Worker worker);
    void onCallBackListOfWorkers(List<WorkerOrders> worker);
    void onCallBackListOfClients(Map<String,User> users);
    void onCallBackMapidEmails(Map<String,Worker> idAndEmails);
}
