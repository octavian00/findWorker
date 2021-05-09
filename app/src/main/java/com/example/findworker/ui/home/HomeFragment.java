package com.example.findworker.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.findworker.R;
import com.example.findworker.helpers.FirebaseHelper;
import com.example.findworker.models.Worker;

import static com.example.findworker.helpers.LoggedUserData.loggedUserEmail;
import static com.example.findworker.helpers.LoggedUserData.loggedUserName;
import static com.example.findworker.helpers.LoggedUserData.regiserUserUUID;

public class HomeFragment extends Fragment {

    Button btn_submit;
    EditText edt_jobtTitle,edt_experience,edt_location;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_worker_view_profile, container, false);
        initializeData(root);
        listener();
        return root;
    }
    private void initializeData(View view){
        btn_submit =view.findViewById(R.id.btn_submit_data);
        edt_jobtTitle = view.findViewById(R.id.edt_jobTitle);
        edt_experience = view.findViewById(R.id.edt_experience);
        edt_location = view.findViewById(R.id.edt_location);
    }
    private void listener(){
        btn_submit.setOnClickListener(v -> {
            Log.d("FILLDATA","START");
            Worker worker =new Worker(loggedUserEmail,loggedUserName,edt_jobtTitle.getText().toString(),Integer.valueOf(edt_experience.getText().toString()),edt_location.getText().toString());
            Log.d("FILLDATA","regiserUserUUID="+regiserUserUUID);
            Log.d("FILLDATA","getJobTitle="+worker.getJobTitle());
            Log.d("FILLDATA","getExperience="+worker.getExperience());
            Log.d("FILLDATA","getLocation="+worker.getLocation());
            FirebaseHelper.userDatabaseReference.child(regiserUserUUID).setValue(worker);
        });
    }
}