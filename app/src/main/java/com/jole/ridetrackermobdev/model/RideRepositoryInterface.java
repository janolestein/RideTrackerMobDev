package com.jole.ridetrackermobdev.model;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.Optional;

public interface RideRepositoryInterface
{
    public LiveData<double[]> getRideServiceUiState();

    public void setRideServiceUiState(double[] rideServiceUiState);

    public void addNewRide(Ride ride);

    public void removeRide(Ride ride);

    public LiveData<List<Ride>> getAllRidesList();

    public Optional<Ride> findRideById(int id);
}
