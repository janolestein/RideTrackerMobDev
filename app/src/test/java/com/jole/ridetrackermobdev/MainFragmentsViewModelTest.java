package com.jole.ridetrackermobdev;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jole.ridetrackermobdev.controller.MainFragmentsViewModel;
import com.jole.ridetrackermobdev.model.DaoInterface;
import com.jole.ridetrackermobdev.model.Ride;
import com.jole.ridetrackermobdev.model.RideDatabase;
import com.jole.ridetrackermobdev.model.RideRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.osmdroid.util.GeoPoint;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;



public class MainFragmentsViewModelTest
{
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule =
            new InstantTaskExecutorRule();
    List<GeoPoint> gPoints;
    Ride ride;
    @Mock
    RideRepository repo;
    LiveData<List<Ride>> lRideList;
    MutableLiveData<double[]> rideServiceUiState;
    MainFragmentsViewModel vModel;


    @Before
    public void setUp() throws Exception
    {
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

        ride = new Ride("Wednesday Evening Ride", LocalDate.now().toString(), 60, 25.6, 1.45, gPoints);

        rideServiceUiState = new MutableLiveData<double[]>(new double[]{3453, 56756, 2342342});
        ArrayList<Ride> aList = new ArrayList<>();
        aList.add(ride);
        aList.add(ride);
        lRideList = new MutableLiveData<>(aList);
        when(repo.getAllRidesList()).thenReturn(lRideList);
        when(repo.getRideServiceUiState()).thenReturn(rideServiceUiState);
        vModel = new MainFragmentsViewModel(repo);

    }

    @Test
    public void testGetAllRidesList(){
        Mockito.verify(repo, times(1)).getAllRidesList();
        assertEquals(vModel.getAllRides(), lRideList);
    }

    @Test
    public void testGetUiState(){
        Mockito.verify(repo, times(1)).getRideServiceUiState();
        assertEquals(vModel.getUiState(), rideServiceUiState);
    }
}