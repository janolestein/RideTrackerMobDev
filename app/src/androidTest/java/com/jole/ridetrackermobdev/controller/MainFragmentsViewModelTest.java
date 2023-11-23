package com.jole.ridetrackermobdev.controller;

import static org.junit.Assert.*;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.jole.ridetrackermobdev.model.DaoInterface;
import com.jole.ridetrackermobdev.model.Ride;
import com.jole.ridetrackermobdev.model.RideDatabase;

import org.junit.Before;
import org.junit.Rule;
import org.osmdroid.util.GeoPoint;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;

@HiltAndroidTest
public class MainFragmentsViewModelTest
{
    @Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule =
            new InstantTaskExecutorRule();

    @Inject
    DaoInterface rideDb;
    @Inject
    RideDatabase rideDatabaseLiteral;
    List<GeoPoint> gPoints;
    Ride ride;


    @Before
    public void setUp() throws Exception
    {
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
}