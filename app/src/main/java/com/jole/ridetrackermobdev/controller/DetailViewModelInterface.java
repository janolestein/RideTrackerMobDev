package com.jole.ridetrackermobdev.controller;

import com.jole.ridetrackermobdev.model.Ride;

import java.util.Optional;

public interface DetailViewModelInterface {
    /**
     * Return a Ride identified by an ID if could be found
     * @param id of Ride to be searched
     * @return Optional of a Ride can be empty if Ride could not be found
     */
    public Optional<Ride> findRideById(int id);

    /**
     * Deletes a ride
     * @param ride ride to delete
     */
    public void deleteRide(Ride ride);
}
