package com.jole.ridetrackermobdev.model;

import java.util.ArrayList;

public interface ModelInterface
{

    boolean addNewRide(Ride ride);

    boolean removeRide(Ride ride);

    ArrayList<Ride> getAllRidesList();

}
