package com.jole.ridetrackermobdev.model;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.Optional;

public interface RideRepositoryInterface
{
    /**
     *Gets the State set by Service for displaying in a UI
     * @return Double Array as Live-Data to be observed by a UI
     */
    public LiveData<double[]> getRideServiceUiState();

    /**
     * Sets the Data made by the Service to be displayed in a UI
     * @param rideServiceUiState as a double Array
     */
    public void setRideServiceUiState(double[] rideServiceUiState);

    /**
     * Adds a new Ride to the Database
     * @param ride Object to be added to the DB
     */
    public void addNewRide(Ride ride);

    /**
     * Removes a Ride from the Database
     * @param ride object to be removed
     */
    public void removeRide(Ride ride);

    /**
     * Gets a List of all Ride Objects in the Database
     * @return List of all Rides as LiveData<List>
     */
    public LiveData<List<Ride>> getAllRidesList();

    /**
     * Searches for a Ride with a Id and returns the Ride
     * @param id of the ride to be retrieved
     * @return Optional of Ride Object
     */
    public Optional<Ride> findRideById(int id);
}
