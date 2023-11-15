package com.jole.ridetrackermobdev.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Dao
public interface DaoInterface
{

    @Insert
    void addNewRide(Ride ride);
    @Delete
    void removeRide(Ride ride);
    @Query("SELECT * FROM Ride")
    LiveData<List<Ride>> getAllRidesList();
    @Query("SELECT * FROM Ride WHERE id LIKE :id LIMIT 1")
    Optional<Ride> findRideById(int id);

}
