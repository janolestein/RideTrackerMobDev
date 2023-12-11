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
import com.jole.ridetrackermobdev.model.RideRepositoryInterface;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * ViewModel for the Fragments in the Main Activity
 * Extends Android ViewModel
 */
@HiltViewModel
public class MainFragmentsViewModel extends ViewModel implements MainViewModelInterface{
    private LiveData<List<Ride>> allRides;

    RideRepositoryInterface rideRepository;

    private LiveData<double[]> uiState;

    /**
     * Constructor for the ViewModel
     * @param rideRepository injected by DaggerHilt
     */
    @Inject
    public MainFragmentsViewModel(RideRepositoryInterface rideRepository) {
        this.rideRepository = rideRepository;
        this.allRides = rideRepository.getAllRidesList();
        this.uiState = rideRepository.getRideServiceUiState();
    }

    /**
     * Return All Rides from the Database as LiveData
     * @return LiveData<List<Ride>>
     */
    public LiveData<List<Ride>> getAllRides() {
        return allRides;
    }

    /**
     * Returns the Service UI-State for use in the UI
     * @return LiveData<double[]>
     */
    public LiveData<double[]> getUiState() {
        return uiState;
    }


}


