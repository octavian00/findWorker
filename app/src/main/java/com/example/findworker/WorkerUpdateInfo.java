package com.example.findworker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class WorkerUpdateInfo extends AppCompatActivity {
    private EditText edt_description;
    private Spinner spinner_specializations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_update_info);
        initializeViews();
    }
    private void initializeViews(){
        edt_description = findViewById(R.id.edt_descriptionWorker);
        spinner_specializations = findViewById(R.id.sp_specializations);
        populateSpinner();
    }
    private void populateSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.specializations_array,
                R.layout.support_simple_spinner_dropdown_item
        );
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_specializations.setAdapter(adapter);
    }
}