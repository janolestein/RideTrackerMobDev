package com.jole.ridetrackermobdev.controller;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.jole.ridetrackermobdev.model.Ride;
import com.jole.ridetrackermobdev.model.RideRepository;

import java.util.Optional;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RideDetailViewModel extends ViewModel
{
    RideRepository rideRepository;


    @Inject
    public RideDetailViewModel(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    public Optional<Ride> findRideById(int id)
    {
        return rideRepository.findRideById(id);
    }
}
