package com.example.findworker.models;

import java.util.ArrayList;
import java.util.List;

public class UserReview extends User{
    private ArrayList<String> workersForReview;
    public UserReview() {}

    public UserReview(String username, String password, ArrayList<String> workersForReview) {
        super(username, password);
        this.workersForReview = workersForReview;
    }

    public List<String> getWorkersForReview() {
        return workersForReview;
    }

    public void setWorkersForReview(ArrayList<String> workersForReview) {

        this.workersForReview = workersForReview;
    }


    public void addWorkerForReview(String workerUUID){
        initilizeWorkerList();
        workersForReview.add(workerUUID);
    }
    public   boolean isAlreadyAdded(String workerUUID){
        if(workersForReview == null)
            return  false;
        for(String s:workersForReview) {
            if(s.equals(workerUUID)) return  true;
        }
        return false;
    }
    private void initilizeWorkerList(){
        if(this.workersForReview == null){
            this.workersForReview = new ArrayList<>();
        }
    }
}
