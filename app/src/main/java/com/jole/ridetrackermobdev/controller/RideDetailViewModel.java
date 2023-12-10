package com.jole.ridetrackermobdev.controller;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.jole.ridetrackermobdev.model.Ride;
import com.jole.ridetrackermobdev.model.RideRepository;
import com.jole.ridetrackermobdev.model.RideRepositoryInterface;

import java.util.Optional;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * ViewModel for the Detail UIs
 * Extends Android ViewModel
 */
@HiltViewModel
public class RideDetailViewModel extends ViewModel
{
    RideRepositoryInterface rideRepository;

    /**
     * Constructor for the ViewModel
     * @param rideRepository injected by DaggerHilt
     */
    @Inject
    public RideDetailViewModel(RideRepositoryInterface rideRepository) {
        this.rideRepository = rideRepository;
    }

    public Optional<Ride> findRideById(int id)
    {
        return rideRepository.findRideById(id);
    }
}
