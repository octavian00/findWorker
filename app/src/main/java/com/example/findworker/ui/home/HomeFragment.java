package com.example.findworker.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.findworker.R;
import com.example.findworker.helpers.FirebaseHelper;
import com.example.findworker.helpers.LoggedUserData;
import com.example.findworker.models.Worker;
import com.example.findworker.models.WorkerOrders;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.example.findworker.helpers.LoggedUserData.loggedUserEmail;
import static com.example.findworker.helpers.LoggedUserData.loggedUserName;
import static com.example.findworker.helpers.LoggedUserData.regiserUserUUID;

public class HomeFragment extends Fragment {

    Button btn_submit;
    EditText edt_jobtTitle,edt_experience,edt_location,edt_phoneNumber;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_worker_view_profile, container, false);
        initializeData(root);
        getWorkerFromDb();
        return root;
    }
    private void initializeData(View view){
        btn_submit =view.findViewById(R.id.btn_submit_data);
        edt_jobtTitle = view.findViewById(R.id.edt_jobTitle);
        edt_experience = view.findViewById(R.id.edt_experience);
        edt_location = view.findViewById(R.id.edt_location);
        edt_phoneNumber = view.findViewById(R.id.edt_phoneNumber);
    }
    private void listener(WorkerOrders workerOrders){
        btn_submit.setOnClickListener(v -> {
            Worker worker = new WorkerOrders(workerOrders.getEmail(),workerOrders.getUsername(),
                    edt_jobtTitle.getText().toString(),Integer.valueOf(edt_experience.getText().toString()),
                    edt_location.getText().toString(),edt_phoneNumber.getText().toString(),workerOrders.getPendingOrders(),
                    workerOrders.getReviews(),workerOrders.getAverage(),workerOrders.getNumberOfReviews());
            //Worker worker =new Worker(loggedUserEmail,loggedUserName,edt_jobtTitle.getText().toString(),Integer.valueOf(edt_experience.getText().toString()),edt_location.getText().toString());
            FirebaseHelper.userDatabaseReference.child(regiserUserUUID).setValue(worker);
        });
    }
    private void getWorkerFromDb(){
        FirebaseHelper.getInstance();
        FirebaseHelper.userDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                WorkerOrders workerOrders = snapshot.child(regiserUserUUID).getValue(WorkerOrders.class);
                populateViews(workerOrders);
                listener(workerOrders);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void populateViews(WorkerOrders workerOrders){
        if(workerOrders!=null) {
            if (workerOrders.getJobTitle() != null) {
                edt_jobtTitle.setText(workerOrders.getJobTitle(), TextView.BufferType.EDITABLE);
            }
            if (workerOrders.getExperience() != null) {
                edt_experience.setText(workerOrders.getExperience().toString(), TextView.BufferType.EDITABLE);
            }
            if (workerOrders.getLocation() != null) {
                edt_location.setText(workerOrders.getLocation(), TextView.BufferType.EDITABLE);
            }
            if (workerOrders.getPhoneNumber() != null) {
                Log.d("HomeFragment",workerOrders.getPhoneNumber());
               edt_phoneNumber.setText(workerOrders.getPhoneNumber(), TextView.BufferType.EDITABLE);
            }
        }
    }
}