package com.jole.ridetrackermobdev.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.osmdroid.util.GeoPoint;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.inject.Inject;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

@HiltAndroidTest
public class DaoTest {
    @Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);
    @Inject
    DaoInterface rideDb;
    @Inject
    RideDatabase rideDatabaseLiteral;
    List<GeoPoint> gPoints;
    Ride ride;
    List<Ride> allRidesList;


    @Before
    public void init() {
        hiltRule.inject();

        gPoints = new LinkedList<>();
        gPoints.add(new GeoPoint(52.458159970620216, 13.527038899381642));
        gPoints.add(new GeoPoint(52.46051831693104, 13.521824258809318));
        gPoints.add(new GeoPoint(52.46187056912226, 13.516528352449457));
        gPoints.add(new GeoPoint(52.46330523870933, 13.510321300073972));
        gPoints.add(new GeoPoint(52.462761066704125, 13.506884073289987));
        gPoints.add(new GeoPoint(52.46080964195072, 13.503933924512413));
        gPoints.add(new GeoPoint(52.46082614030988, 13.501795751994008));
        gPoints.add(new GeoPoint(52.45821491925012, 13.497420340748592));
        gPoints.add(new GeoPoint(52.46126039973983, 13.4932430495305));
        gPoints.add(new GeoPoint(52.46363503504482, 13.489868695841839));
        gPoints.add(new GeoPoint(52.4661139469194, 13.495570882101445));

        ride = new Ride("Wednesday Evening Ride", "This is a Example Ride Description", LocalDate.now().toString(), 60, 25.6, 1.45,
                "https://static-maps.alltrails.com/production/at-map/132570830/v1-trail-england-northumberland-holy-island-bicycle-ride-at-map-132570830-1689185982-327w203h-en-US-i-2-style_3.png", gPoints);
        rideDatabaseLiteral.clearAllTables();


    }

    @Test
    public void testInsertAndFind() {

        rideDb.addNewRide(ride);
        assertEquals(rideDb.findRideById(1).get().getName(), ride.getName());
        assertEquals(rideDb.findRideById(1).get().getDescription(), ride.getDescription());
        assertEquals(rideDb.findRideById(1).get().getDate(), ride.getDate());
        assertEquals(rideDb.findRideById(1).get().getRideLengthKm(), ride.getRideLengthKm(), 0);
        assertEquals(rideDb.findRideById(1).get().getAverageSpeed(), ride.getAverageSpeed(), 0);
        assertEquals(rideDb.findRideById(1).get().getTotalRideTime(), ride.getTotalRideTime(), 0);
        assertEquals(rideDb.findRideById(1).get().getImgUrl(), ride.getImgUrl());
        assertEquals(rideDb.findRideById(1).get().getGeoPoints(), ride.getGeoPoints());
    }

    @Test
    public void testMultipleInsert() {

        rideDb.addNewRide(ride);
        rideDb.addNewRide(ride);
        rideDb.addNewRide(ride);
        rideDb.addNewRide(ride);
        rideDb.addNewRide(ride);
        rideDb.addNewRide(ride);
        rideDb.addNewRide(ride);
        assertEquals(rideDb.findRideById(7).get().getName(), ride.getName());
        assertEquals(rideDb.findRideById(7).get().getDescription(), ride.getDescription());
        assertEquals(rideDb.findRideById(7).get().getDate(), ride.getDate());
        assertEquals(rideDb.findRideById(7).get().getRideLengthKm(), ride.getRideLengthKm(), 0);
        assertEquals(rideDb.findRideById(7).get().getAverageSpeed(), ride.getAverageSpeed(), 0);
        assertEquals(rideDb.findRideById(7).get().getTotalRideTime(), ride.getTotalRideTime(), 0);
        assertEquals(rideDb.findRideById(7).get().getImgUrl(), ride.getImgUrl());
        assertEquals(rideDb.findRideById(7).get().getGeoPoints(), ride.getGeoPoints());
    }

    @Test
    public void testNullFind() {

        assertThrows(NoSuchElementException.class, () -> rideDb.findRideById(500).get());
    }

    @Test
    public void testDelete() {
        rideDb.addNewRide(ride);
        assertEquals(rideDb.findRideById(1).get().getName(), ride.getName());
        Ride rideDel = rideDb.findRideById(1).get();
        rideDb.removeRide(rideDel);
        assertThrows(NoSuchElementException.class, () -> rideDb.findRideById(1).get());
    }

    @Test
    public void testGetAll() {
        rideDb.addNewRide(ride);
        rideDb.addNewRide(ride);
        rideDb.addNewRide(ride);
        rideDb.addNewRide(ride);
        rideDb.addNewRide(ride);
        rideDb.addNewRide(ride);
        rideDb.addNewRide(ride);
        LifecycleOwner lifecycleOwner  = Mockito.mock(LifecycleOwner.class);
        LifecycleRegistry lifecycle = new LifecycleRegistry(Mockito.mock(LifecycleOwner.class));
        lifecycle.setCurrentState(Lifecycle.State.RESUMED);
        Mockito.when(lifecycleOwner.getLifecycle()).thenReturn(lifecycle);



        rideDb.getAllRidesList().observe(lifecycleOwner, new Observer<List<Ride>>() {
            @Override
            public void onChanged(List<Ride> rides) {
                allRidesList = rides;
            }
        });
        assertEquals(7, allRidesList.size());


        assertEquals(7, rideDb.getAllRidesListSync().size());
        Ride rideTemp = rideDb.findRideById(7).get();
        rideDb.removeRide(rideTemp);
        assertEquals(6, rideDb.getAllRidesListSync().size());


    }


}
