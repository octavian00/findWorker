package com.example.findworker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.example.findworker.profile.UserProfile;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

public class SelectRole extends AppCompatActivity {
    FusedLocationProviderClient fusedLocationProviderClient;
    SharedPreferences prefs;
    boolean isLocationReady=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        showlocation();
        initializeSharedPreference();
    }

    public void workerUpdate(View view) {
        saveToSharedPreference(1);
        startActivity(new Intent(getApplicationContext(), WorkerProfile.class));
    }

    public void userProfile(View view) {
        saveToSharedPreference(0);
        showlocation();
        new CountDownTimer(6500,1){
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("LOCATION","TIMER");
            }

            @Override
            public void onFinish() {
                if(isLocationReady) {
                    startActivity(new Intent(getApplicationContext(), UserProfile.class));
                }
            }
        }.start();

    }

    private void showlocation() {
            if (ActivityCompat.checkSelfPermission(SelectRole.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(SelectRole.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
                        Location location = task.getResult();
                        Log.d("LOCATION", "START");
                        if (location != null) {
                            getLocation(location);
                        } else {
                            LocationRequest locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(10000).setFastestInterval(1000).setNumUpdates(1);
                            LocationCallback locationCallback = new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);
                                    Location location1 = locationResult.getLastLocation();
                                    if(location1!=null) {
                                        getLocation(location1);
                                    }
                                    Log.d("LOCATION1", String.valueOf(location1.getLongitude()));
                                }
                            };
                            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                        }
                        Log.d("LOCATION", "END");
                    });
                } else {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            } else {
                Log.d("LOCATION", "NO ACCES");
                ActivityCompat.requestPermissions(SelectRole.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);

            }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode ==100&&grantResults.length>0 &&(grantResults[0]+grantResults[1])==PackageManager.PERMISSION_GRANTED){
            showlocation();
        }
    }

    private void initializeSharedPreference() {
        prefs = getSharedPreferences("preference.txt", MODE_PRIVATE);
    }

    private void saveToSharedPreference(int userType) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("userType", String.valueOf(userType));
        editor.apply();
    }
    private void getLocation(Location location){
            Geocoder geocoder = new Geocoder(SelectRole.this, Locale.getDefault());
            try {
                isLocationReady =true;
                List<Address> addresses = geocoder.getFromLocation(
                        location.getLatitude(), location.getLongitude(), 1);
                Log.d("LOCATION2", addresses.get(0).getAddressLine(0));
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

}