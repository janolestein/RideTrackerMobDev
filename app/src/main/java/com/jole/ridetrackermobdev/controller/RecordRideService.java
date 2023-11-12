package com.jole.ridetrackermobdev.controller;

import static androidx.core.app.NotificationCompat.PRIORITY_MAX;
import android.os.Binder;
import android.os.IBinder;
import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.jole.ridetrackermobdev.model.RideDao;
import com.jole.ridetrackermobdev.model.Ride;

import org.osmdroid.util.GeoPoint;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;


public class RecordRideService extends Service
{
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private LocalBroadcastManager broadcaster;
    private double latitude, longitude;
    private List<GeoPoint> geoPointList;
    private GeoPoint lastKnownGeoPoint;
    public static Boolean isRunning = false;
    private double dist = 0D;
    private double elapsedTime = 0D;
    private double startTime = 0D;
    private double avSpeed = 0D;

    public RecordRideService()
    {
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return new Binder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.v("ABC", "service started");


        startForeground(1001, getNotification());
        Log.v("ABC", "onCreate");
        broadcaster = LocalBroadcastManager.getInstance(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(500)
                .setMaxUpdateDelayMillis(1000)
                .build();
        locationCallback = new LocationCallback()
        {
            @Override
            public void onLocationResult(LocationResult locationResult)
            {
                Log.v("ABC", "onLocationResult");
                if (locationResult == null)
                {
                    return;
                }
                for (Location location : locationResult.getLocations())
                {
                    if (location != null)
                    {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        GeoPoint current = new GeoPoint(latitude, longitude);
                        Log.v("ABC", Double.toString(latitude));
                        geoPointList.add(current);
                        if (lastKnownGeoPoint == null)
                        {
                            lastKnownGeoPoint = current;
                        } else
                        {
                            elapsedTime = System.currentTimeMillis() - startTime;
                            dist += Util.distanceBetweenTwoGeoPoints(lastKnownGeoPoint, current);
                            avSpeed = dist / (elapsedTime / (1000 *  (60 * 60)));
                            Log.v("ABC", Double.toString(dist));
                            lastKnownGeoPoint = current;
                            sendResult(dist, elapsedTime, avSpeed);
                        }


                    }
                }
            }
        };

        startLocationUpdates();

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onCreate()
    {
        isRunning = true;
        geoPointList = new LinkedList<>();
        startTime = System.currentTimeMillis();
        super.onCreate();

    }

    @Override
    public void onDestroy()
    {
        mFusedLocationClient.removeLocationUpdates(locationCallback);
        saveRide();
        isRunning = false;
        Log.v("ABC", geoPointList.toString());
        super.onDestroy();
    }

    public void saveRide()
    {
        RideDao.getInstance().addNewRide(new Ride("Wednesday Evening Ride", "This is a Example Ride Description", LocalDate.now(), dist, avSpeed, elapsedTime,
                "https://static-maps.alltrails.com/production/at-map/132570830/v1-trail-england-northumberland-holy-island-bicycle-ride-at-map-132570830-1689185982-327w203h-en-US-i-2-style_3.png", geoPointList));

    }


    /*
    UTILS
     */
    private void startLocationUpdates()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "You need to grant Location Permission to track your ride", Toast.LENGTH_SHORT).show();
            stopSelf();
        }
        mFusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }

    public void sendResult(double dist, double elapsedTime, double avSpeed)
    {
        Intent intent = new Intent();
        intent.setAction("loc");
        intent.putExtra("distance", dist);
        intent.putExtra("elapsedTime", elapsedTime);
        intent.putExtra("avSpeed", avSpeed);
        Log.v("ABC", "sendresult");
        broadcaster.sendBroadcast(intent);
    }

    public Notification getNotification()
    {
        String channel;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            channel = createChannel();
        else
        {
            channel = "";
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat
                .Builder(this, channel)
                .setSmallIcon(android.R.drawable.ic_menu_mylocation)
                .setContentTitle("Your Ride is being tracked");

        return mBuilder
                .setPriority(PRIORITY_MAX)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
    }

    @NonNull
    private synchronized String createChannel()
    {
        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel mChannel = new NotificationChannel("TrackingService", "name", NotificationManager.IMPORTANCE_HIGH);

        if (mNotificationManager != null)
        {
            mNotificationManager.createNotificationChannel(mChannel);
        } else
        {
            stopSelf();
        }
        return "TrackingService";
    }


}