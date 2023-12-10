package com.jole.ridetrackermobdev.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Interface that functions as the Basis of the Rooms Database
 */
@Dao
public interface DaoInterface
{
    /**
     * Adds a new Ride to the Database
     * @param ride Object to be added to the DB
     */
    @Insert
    void addNewRide(Ride ride);

    /**
     * Removes a Ride from the Database
     * @param ride object ro to be removed
     */
    @Delete
    void removeRide(Ride ride);

    /**
     * Gets a List of all Ride Objects in the Database
     * @return List of all Rides as LiveData<List>
     */
    @Query("SELECT * FROM Ride")
    LiveData<List<Ride>> getAllRidesList();

    /**
     * Searches for a Ride with a Id and returns the Ride
     * @param id of the ride to be retrieved
     * @return Optional of Ride Object
     */
    @Query("SELECT * FROM Ride WHERE id LIKE :id LIMIT 1")
    Optional<Ride> findRideById(int id);

}
