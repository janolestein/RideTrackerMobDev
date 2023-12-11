package com.jole.ridetrackermobdev.model;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.osmdroid.util.GeoPoint;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;


public class RideRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule =
            new InstantTaskExecutorRule();
    @Mock
    DaoInterface dao;
    RideRepositoryInterface repo;
    List<GeoPoint> gPoints;
    Ride ride;
    double[] liveDataArr;
    private CountDownLatch lock = new CountDownLatch(4);
    LiveData<List<Ride>> lRideList;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

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
        ArrayList<Ride> aList = new ArrayList<>();
        aList.add(ride);
        aList.add(ride);
        lRideList = new MutableLiveData<>(aList);
        when(dao.getAllRidesList()).thenReturn(lRideList);
        repo = new RideRepository(dao);




    }

    @Test
    public void testInsert() throws InterruptedException {

        repo.addNewRide(ride);
        lock.countDown();
        lock.await(5000, TimeUnit.MILLISECONDS);
        Mockito.verify(dao, Mockito.times(1)).addNewRide(ride);
        repo.addNewRide(ride);
        lock.countDown();
        lock.await(5000, TimeUnit.MILLISECONDS);
        Mockito.verify(dao, Mockito.times(2)).addNewRide(ride);

    }

    @Test
    public void testFindById() throws InterruptedException {

        repo.findRideById(1);
        lock.countDown();
        lock.await(5000, TimeUnit.MILLISECONDS);
        Mockito.verify(dao, Mockito.times(1)).findRideById(1);
        repo.findRideById(2);
        lock.countDown();
        lock.await(5000, TimeUnit.MILLISECONDS);
        Mockito.verify(dao, Mockito.times(1)).findRideById(2);
        repo.findRideById(1);
        lock.countDown();
        lock.await(5000, TimeUnit.MILLISECONDS);
        Mockito.verify(dao, Mockito.times(2)).findRideById(1);
        Optional<Ride> oRide = Optional.ofNullable(ride);
        when(dao.findRideById(23)).thenReturn(oRide);
        Ride tempRide = repo.findRideById(23).get();
        lock.countDown();
        lock.await(5000, TimeUnit.MILLISECONDS);
        assertEquals(oRide.get(), tempRide);

    }



    @Test
    public void testDelete() throws InterruptedException {
        repo.removeRide(ride);
        lock.countDown();
        lock.await(5000, TimeUnit.MILLISECONDS);
        Mockito.verify(dao, Mockito.times(1)).removeRide(ride);
        repo.removeRide(ride);
        lock.countDown();
        lock.await(5000, TimeUnit.MILLISECONDS);
        Mockito.verify(dao, Mockito.times(2)).removeRide(ride);
    }

    @Test
    public void testGetAll() throws InterruptedException {

        Mockito.verify(dao, Mockito.times(1)).getAllRidesList();


        assertEquals(repo.getAllRidesList(), lRideList);

    }

    @Test
    public void testGetUiStateLiveData(){

        repo.getRideServiceUiState().observeForever(new Observer<double[]>()
        {
            @Override
            public void onChanged(double[] doubles)
            {
                liveDataArr = doubles;
            }
        });

        assertTrue(Arrays.equals(liveDataArr, new double[]{0, 0, 0}));
    }

    @Test
    public void testSetUiStateLiveData(){
        repo.setRideServiceUiState(new double[]{45,768,234});
        repo.getRideServiceUiState().observeForever(new Observer<double[]>()
        {
            @Override
            public void onChanged(double[] doubles)
            {
                liveDataArr = doubles;
            }
        });

        assertTrue(Arrays.equals(liveDataArr, new double[]{45,768,234}));
    }
}