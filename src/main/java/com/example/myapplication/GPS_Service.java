package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;

public class GPS_Service extends Service {

    private LocationListener listener;
    private LocationManager manager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Intent i = new Intent("location_update");
                i.putExtra("coordinates", location.getLongitude()+ " " + location.getLatitude());
                sendBroadcast(i);

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }
        };

        manager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,0,listener);
    }

    @Override
    public void onDestroy() {
        if(manager != null){
            manager.removeUpdates(listener);
        }
    }
}
