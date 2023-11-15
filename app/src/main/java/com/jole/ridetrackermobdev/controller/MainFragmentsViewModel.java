package com.jole.ridetrackermobdev.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.jole.ridetrackermobdev.model.Ride;
import com.jole.ridetrackermobdev.model.RideRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainFragmentsViewModel extends ViewModel {
    private LiveData<List<Ride>> allRides;

    RideRepository rideRepository;

    private LiveData<double[]> uiState;

    @Inject
    public MainFragmentsViewModel(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
        this.allRides = rideRepository.getAllRidesList();
        this.uiState = rideRepository.getRideServiceUiState();
    }

    public LiveData<List<Ride>> getAllRides() {
        return allRides;
    }

    public LiveData<double[]> getUiState() {
        return uiState;
    }


}


