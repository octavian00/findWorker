package com.example.findworker;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.findworker.ui.dashboard.DashboardFragment;
import com.example.findworker.ui.home.HomeFragment;
import com.example.findworker.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class WorkerProfile extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private HomeFragment homeFragment;
    private DashboardFragment dashboardFragment;
    private NotificationsFragment notificationsFragment;
    private BottomNavigationView bottomNavigationView;
    private Fragment activeFragment;
    final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigation_home:
                removeFragments();
                fragmentManager.beginTransaction().add(R.id.nav_host_fragment,homeFragment,"1").hide(activeFragment).show(homeFragment).commit();
                activeFragment = homeFragment;
                return true;
            case R.id.navigation_dashboard:
                removeFragments();
                fragmentManager.beginTransaction().add(R.id.nav_host_fragment,dashboardFragment,"2").hide(activeFragment).show(dashboardFragment).commit();
                activeFragment = dashboardFragment;
                return true;
            case R.id.navigation_notifications:
                removeFragments();
                fragmentManager.beginTransaction().add(R.id.nav_host_fragment,notificationsFragment,"3").hide(activeFragment).show(notificationsFragment).commit();
                activeFragment = notificationsFragment;
                return true;
            default:return false;
        }
    }

    private void removeFragments() {
        fragmentManager.beginTransaction().remove(homeFragment).commitNow();
        fragmentManager.beginTransaction().remove(dashboardFragment).commitNow();
        fragmentManager.beginTransaction().remove(notificationsFragment).commitNow();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_profile);
        initializeViews();
        LoadFragment();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private void LoadFragment() {
        fragmentManager.beginTransaction().add(R.id.nav_host_fragment, notificationsFragment, "3").hide(notificationsFragment).commit();
        fragmentManager.beginTransaction().add(R.id.nav_host_fragment, dashboardFragment, "2").hide(dashboardFragment).commit();
        fragmentManager.beginTransaction().add(R.id.nav_host_fragment, homeFragment, "1").commit();
    }

    private void initializeViews() {
        bottomNavigationView = findViewById(R.id.nav_view);
        homeFragment = new HomeFragment();
        dashboardFragment = new DashboardFragment();
        notificationsFragment = new NotificationsFragment();
        activeFragment = homeFragment;
    }

}