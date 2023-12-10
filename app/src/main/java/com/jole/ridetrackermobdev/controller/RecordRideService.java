package com.jole.ridetrackermobdev.controller;

import static androidx.core.app.ActivityCompat.requestPermissions;
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
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.jole.ridetrackermobdev.model.DaoInterface;

import com.jole.ridetrackermobdev.model.Ride;
import com.jole.ridetrackermobdev.model.RideRepository;
import com.jole.ridetrackermobdev.model.RideRepositoryInterface;

import org.osmdroid.util.GeoPoint;

import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Service to Record a Ride
 * Acts as Android Foreground Service and uses a FusedLocationProvider for the Location Data
 */
@AndroidEntryPoint
public class RecordRideService extends Service
{
    private final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 42;
    @Inject
    FusedLocationProviderClient mFusedLocationClient;
    @Inject
    LocationRequest locationRequest;
    public LocationCallback locationCallback;
    @Inject
    LocalBroadcastManager broadcaster;
    private double latitude, longitude;
    private List<GeoPoint> geoPointList = new LinkedList<>();;
    private GeoPoint lastKnownGeoPoint;
    public static Boolean isRunning = false;
    private double dist = 0D;
    private double elapsedTime = 0D;
    private double startTime;
    private double avSpeed = 0D;
    @Inject
    RideRepositoryInterface rideRepository;
    @Inject
    Clock clock;

    /**
     * Standard Empty Constructor
     */
    public RecordRideService(){

    }

    /**
     * Only for testing purposes
     * @param rideRepository
     */
    public RecordRideService(RideRepository rideRepository)
    {
        this.rideRepository = rideRepository;
    }

    /**
     * Mostly for testing
     *
     * @param intent The Intent that was used to bind to this service,
     * as given to {@link android.content.Context#bindService
     * Context.bindService}.  Note that any extras that were included with
     * the Intent at that point will <em>not</em> be seen here.
     *
     * @return
     */
    @Override
    public IBinder onBind(Intent intent)
    {
        return new Binder();
    }

    /**
     * Registers the Location Callback and starts Location Updates for the Service
     * @param intent The Intent supplied to {@link android.content.Context#startService},
     * as given.  This may be null if the service is being restarted after
     * its process has gone away, and it had previously returned anything
     * except {@link #START_STICKY_COMPATIBILITY}.
     * @param flags Additional data about this start request.
     * @param startId A unique integer representing this specific request to
     * start.  Use with {@link #stopSelfResult(int)}.
     *
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.v("ABC", "service started");
        startForeground(1001, getNotification());
        Log.v("ABC", "onCreate");

        registerLocationCallback(clock);
        startLocationUpdates();

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * Overrides the Callback function of the FusedLocationProvider
     * @param clock needed to keep the time the service started and elapsed between Callbacks
     */
    public void registerLocationCallback(Clock clock){
        startTime = clock.millis();
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
                            elapsedTime = clock.millis() - startTime;
                            dist += Util.distanceBetweenTwoGeoPoints(lastKnownGeoPoint, current);
                            avSpeed = dist / (elapsedTime / (1000 *  (60 * 60)));
                            Log.v("ABC", Double.toString(dist));
                            lastKnownGeoPoint = current;
                            rideRepository.setRideServiceUiState(new double[]{dist, elapsedTime, avSpeed});
                        }


                    }
                }
            }
        };
    }

    /**
     * Sets the static Variable isRunning to true
     */
    @Override
    public void onCreate()
    {
        isRunning = true;
        super.onCreate();
    }

    /**
     * Saves the Recorded Ride and unregisters the Callback from the FusedLocationProvider on teardown of the Service
     */
    @Override
    public void onDestroy()
    {
        mFusedLocationClient.removeLocationUpdates(locationCallback);
        saveRide();
        isRunning = false;
        Log.v("ABC", geoPointList.toString());
        super.onDestroy();
    }

    /**
     * Function to Save a Recorded Ride to the Repository
     */
    public void saveRide()
    {
        rideRepository.addNewRide(new Ride("Wednesday Evening Ride", "This is a Example Ride Description", LocalDate.now().toString(), dist, avSpeed, elapsedTime,
                "https://static-maps.alltrails.com/production/at-map/132570830/v1-trail-england-northumberland-holy-island-bicycle-ride-at-map-132570830-1689185982-327w203h-en-US-i-2-style_3.png", geoPointList));
        rideRepository.setRideServiceUiState(new double[]{0, 0, 0});
    }


    /**
     * Registers the Callback with the FusedLocationProvider and requests LocationUpdates
     */
    private void startLocationUpdates()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "You need to grant Location Permission to track your ride", Toast.LENGTH_SHORT).show();
            //stopSelf();
        }
        mFusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }

    /**
     * Notification Utilities neccecary for the Foreground Service
     * @return a Notification to be displayed while the Service is running
     */
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

    /**
     * Creates a Notification Channel to display the Notification from the Service
     * @return String to identify the Channel
     */
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