package com.jole.ridetrackermobdev.model;

import java.util.ArrayList;

public interface ModelInterface
{

    public boolean addNewRide(Ride ride);

    public boolean removeRide(Ride ride);

    public ArrayList<Ride> getAllRidesList();

}
