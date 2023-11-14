package com.jole.ridetrackermobdev.model;

import java.util.ArrayList;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RideDao implements DaoInterface
{
    private static ArrayList<Ride> rideList;

    private static RideDao instance;

    @Inject
    public RideDao()
    {
        rideList = new ArrayList<>();
    }

    public static RideDao getInstance()
    {
        if (instance == null)
        {
            rideList = new ArrayList<>();
            initData();
            instance = new RideDao();
        }
        return instance;
    }

    private static void initData()
    {

//        rideList.add(new Ride("Wednesday Evening Ride", "This is a Example Ride Description", LocalDate.now(), 60, 25.6, 1.45, "https://static-maps.alltrails.com/production/at-map/132570830/v1-trail-england-northumberland-holy-island-bicycle-ride-at-map-132570830-1689185982-327w203h-en-US-i-2-style_3.png"));
//        rideList.add(new Ride("Monday Noon Ride", "This is a Example Ride Description", LocalDate.now(), 25, 28.7, 3.23, "https://static-maps.alltrails.com/production/at-map/132570830/v1-trail-england-northumberland-holy-island-bicycle-ride-at-map-132570830-1689185982-327w203h-en-US-i-2-style_3.png"));
//        rideList.add(new Ride("Thursday Morning Ride", "This is a Example Ride Description", LocalDate.now(), 25, 28.7, 3.23, "https://static-maps.alltrails.com/production/at-map/132570830/v1-trail-england-northumberland-holy-island-bicycle-ride-at-map-132570830-1689185982-327w203h-en-US-i-2-style_3.png"));
//        rideList.add(new Ride("Sunday Afternoon Ride", "This is a Example Ride Description", LocalDate.now(), 25, 28.7, 3.23, "https://static-maps.alltrails.com/production/at-map/132570830/v1-trail-england-northumberland-holy-island-bicycle-ride-at-map-132570830-1689185982-327w203h-en-US-i-2-style_3.png"));
//        rideList.add(new Ride("Sunday Afternoon Ride", "This is a Example Ride Description", LocalDate.now(), 25, 28.7, 3.23, "https://static-maps.alltrails.com/production/at-map/132570830/v1-trail-england-northumberland-holy-island-bicycle-ride-at-map-132570830-1689185982-327w203h-en-US-i-2-style_3.png"));
//        rideList.add(new Ride("Sunday Afternoon Ride", "This is a Example Ride Description", LocalDate.now(), 25, 28.7, 3.23, "https://static-maps.alltrails.com/production/at-map/132570830/v1-trail-england-northumberland-holy-island-bicycle-ride-at-map-132570830-1689185982-327w203h-en-US-i-2-style_3.png"));
//        rideList.add(new Ride("Sunday Afternoon Ride", "This is a Example Ride Description", LocalDate.now(), 25, 28.7, 3.23, "https://static-maps.alltrails.com/production/at-map/132570830/v1-trail-england-northumberland-holy-island-bicycle-ride-at-map-132570830-1689185982-327w203h-en-US-i-2-style_3.png"));
//        rideList.add(new Ride("Sunday Afternoon Ride", "This is a Example Ride Description", LocalDate.now(), 25, 28.7, 3.23, "https://static-maps.alltrails.com/production/at-map/132570830/v1-trail-england-northumberland-holy-island-bicycle-ride-at-map-132570830-1689185982-327w203h-en-US-i-2-style_3.png"));
//        rideList.add(new Ride("Sunday Afternoon Ride", "This is a Example Ride Description", LocalDate.now(), 25, 28.7, 3.23, "https://static-maps.alltrails.com/production/at-map/132570830/v1-trail-england-northumberland-holy-island-bicycle-ride-at-map-132570830-1689185982-327w203h-en-US-i-2-style_3.png"));

    }

    @Override
    public void addNewRide(Ride ride)
    {
        rideList.add(ride);
    }

    @Override
    public void removeRide(Ride ride)
    {
        rideList.remove(ride);
    }

    @Override
    public ArrayList<Ride> getAllRidesList()
    {
        return rideList;
    }

    public Optional<Ride> findRideById(int id)
    {
        return rideList.stream()
                .filter(r -> r.getId() == id)
                .findAny();

    }
}
