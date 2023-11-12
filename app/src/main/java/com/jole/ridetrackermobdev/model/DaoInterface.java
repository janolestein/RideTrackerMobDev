package com.jole.ridetrackermobdev.model;

import java.util.ArrayList;
import java.util.Optional;

public interface DaoInterface
{

    boolean addNewRide(Ride ride);

    boolean removeRide(Ride ride);

    ArrayList<Ride> getAllRidesList();

    Optional<Ride> findRideById(int id);

}
