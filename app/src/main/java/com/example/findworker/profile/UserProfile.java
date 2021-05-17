package com.example.findworker.profile;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.findworker.R;
import com.example.findworker.profile.user.ui.dashboard.DashboardFragment;
import com.example.findworker.profile.user.ui.home.HomeFragment;
import com.example.findworker.profile.user.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserProfile extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private HomeFragment homeFragment;
    private DashboardFragment dashboardFragment;
    private NotificationsFragment notificationsFragment;
    private Fragment activeFragment;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.user_home:
                removeFragments();
                fragmentManager.beginTransaction().add(R.id.nav_host_fragment_user,homeFragment,"1").hide(activeFragment).show(homeFragment).commit();
                activeFragment = homeFragment;
                return true;
            case R.id.user_dashboard:
                removeFragments();
                fragmentManager.beginTransaction().add(R.id.nav_host_fragment_user,dashboardFragment,"2").hide(activeFragment).show(dashboardFragment).commit();
                activeFragment = dashboardFragment;
                return true;
            case R.id.user_notifications:
                removeFragments();
                fragmentManager.beginTransaction().add(R.id.nav_host_fragment_user,notificationsFragment,"3").hide(activeFragment).show(notificationsFragment).commit();
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
        setContentView(R.layout.activity_user_profile);
        initializeViews();
        LoadFragment();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private void LoadFragment() {
        fragmentManager.beginTransaction().add(R.id.nav_host_fragment_user, notificationsFragment, "3").hide(notificationsFragment).commit();
        fragmentManager.beginTransaction().add(R.id.nav_host_fragment_user, dashboardFragment, "2").hide(dashboardFragment).commit();
        fragmentManager.beginTransaction().add(R.id.nav_host_fragment_user, homeFragment, "1").commit();
    }

    private void initializeViews() {
        bottomNavigationView = findViewById(R.id.nav_view_user);
        homeFragment = new HomeFragment();
        dashboardFragment = new DashboardFragment();
        notificationsFragment = new NotificationsFragment();
        activeFragment = homeFragment;
    }

}