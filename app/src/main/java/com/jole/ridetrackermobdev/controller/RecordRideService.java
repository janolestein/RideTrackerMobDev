package com.jole.ridetrackermobdev.controller;

import static androidx.core.app.NotificationCompat.PRIORITY_LOW;
import static androidx.core.app.NotificationCompat.PRIORITY_MAX;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.jole.ridetrackermobdev.R;

import org.osmdroid.util.GeoPoint;

import java.util.LinkedList;
import java.util.List;


public class RecordRideService extends Service {
    private FusedLocationProviderClient mFusedLocationClient;

    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    private LocalBroadcastManager broadcaster;

    Double latitude, longitude;
    List<GeoPoint> GeoPointList;
    GeoPoint lastKnownGeoPoint;

    public static Boolean isRunning = false;

    Double dist = 0D;

    public RecordRideService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("ABC", "service started");


        startForeground(1001, getNotification());
        Log.v("ABC", "onCreate");
        broadcaster = LocalBroadcastManager.getInstance(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10 * 1000)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(500)
                .setMaxUpdateDelayMillis(1000)
                .build();
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Log.v("ABC", "onLocationResult");
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        GeoPoint current = new GeoPoint(latitude, longitude);
                        Log.v("ABC", Double.toString(latitude));
                        GeoPointList.add(current);
                        if (lastKnownGeoPoint == null)
                        {
                            lastKnownGeoPoint = current;
                        }
                        else {
                            dist += Util.distanceBetweenTwoGeoPoints(lastKnownGeoPoint, current);
                            Log.v("ABC", Double.toString(dist));
                            lastKnownGeoPoint = current;
                            sendResult(dist, longitude);
                        }


                    }
                }
            }
        };

        startLocationUpdates();

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onCreate() {
        isRunning = true;
        GeoPointList = new LinkedList<>();
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFusedLocationClient.removeLocationUpdates(locationCallback);
        isRunning = false;
        Log.v("ABC", GeoPointList.toString());
    }



    /*
    UTILS
     */
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You need to grant Location Permission to track your ride", Toast.LENGTH_SHORT).show();
            onDestroy();
        }
        mFusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }
    public void sendResult(Double latitude, Double longitude) {
        Intent intent = new Intent();
        intent.setAction("loc");
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
        Log.v("ABC", "sendresult");
        broadcaster.sendBroadcast(intent);
    }

    public Notification getNotification() {
        String channel;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            channel = createChannel();
        else {
            channel = "";
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat
                .Builder(this, channel)
                .setSmallIcon(android.R.drawable.ic_menu_mylocation)
                .setContentTitle("Service Running");

        Notification notification = mBuilder
                .setPriority(PRIORITY_MAX)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();

        return notification;
    }

    @NonNull
    @TargetApi(26)
    private synchronized String createChannel() {
        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel mChannel = new NotificationChannel("TrackingService", "name", NotificationManager.IMPORTANCE_HIGH);

        if (mNotificationManager != null) {
            mNotificationManager.createNotificationChannel(mChannel);
        } else {
            stopSelf();
        }
        return "TrackingService";
    }



}