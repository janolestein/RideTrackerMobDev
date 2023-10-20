package com.jole.ridetrackermobdev.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Model implements ModelInterface
{
    private static ArrayList<Ride> rideList;

    private static Model instance;

    private Model()
    {
    }

    public static Model getInstance()
    {
        if(instance == null)
        {
            rideList = new ArrayList<Ride>();
            initData();
            instance = new Model();
        }
        return instance;
    }

    private static void initData()
    {

        rideList.add(new Ride(1, "Wednesday Evening Ride", "This is a Example Ride Description", LocalDate.now() , 600, "https://static-maps.alltrails.com/production/at-map/132570830/v1-trail-england-northumberland-holy-island-bicycle-ride-at-map-132570830-1689185982-327w203h-en-US-i-2-style_3.png"));

        rideList.add(new Ride(2, "Monday Noon Ride", "This is a Example Ride Description", LocalDate.now(), 250, "https://static-maps.alltrails.com/production/at-map/132570830/v1-trail-england-northumberland-holy-island-bicycle-ride-at-map-132570830-1689185982-327w203h-en-US-i-2-style_3.png"));

    }

    @Override
    public boolean addNewRide(Ride ride)
    {
        return rideList.add(ride);
    }

    @Override
    public boolean removeRide(Ride ride)
    {
        return rideList.remove(ride);
    }

    @Override
    public ArrayList<Ride> getAllRidesList()
    {
        return rideList;
    }
}
