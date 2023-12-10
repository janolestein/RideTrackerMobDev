package com.jole.ridetrackermobdev.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * Database Class that describes a Room Database
 * extends RoomDatabase
 */
@Database(entities = {Ride.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class RideDatabase extends RoomDatabase
{
    public abstract DaoInterface daoInterface();
}
