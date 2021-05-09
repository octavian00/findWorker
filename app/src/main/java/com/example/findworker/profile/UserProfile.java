package com.example.findworker.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.findworker.FireBaseCallBack;
import com.example.findworker.R;
import com.example.findworker.helpers.FirebaseHelper;
import com.example.findworker.helpers.LoggedUserData;
import com.example.findworker.models.User;
import com.example.findworker.models.Worker;
import com.example.findworker.models.WorkerOrders;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserProfile extends AppCompatActivity {
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_profile);
    BottomNavigationView navView = findViewById(R.id.nav_view_user);
//    // Passing each menu ID as a set of Ids because each
//    // menu should be considered as top level destinations.
    AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
            R.id.user_home, R.id.user_dashboard, R.id.user_notifications)
            .build();
     NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_user);
     NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
     NavigationUI.setupWithNavController(navView, navController);
}
}