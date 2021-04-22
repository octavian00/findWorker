package com.example.findworker;

import com.example.findworker.models.Worker;

import java.util.List;

public interface FireBaseCallBack {
    void onCallBack(Worker worker);
    void onCallBackListOfWorkers(List<Worker> worker);
}
