package com.jole.ridetrackermobdev.controller;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.jole.ridetrackermobdev.model.Ride;
import com.jole.ridetrackermobdev.model.RideRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainFragmentsViewModel extends ViewModel
{
    private LiveData<List<Ride>> allRides;

    RideRepository rideRepository;

    @Inject
    public MainFragmentsViewModel(RideRepository rideRepository){
        this.rideRepository = rideRepository;
        this.allRides = rideRepository.getAllRidesList();
    }

    public LiveData<List<Ride>> getAllRides(){
        return allRides;
    }


}
