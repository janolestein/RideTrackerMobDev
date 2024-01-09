package com.jole.ridetrackermobdev.hilt;

import android.content.Context;

import androidx.room.Room;

import com.jole.ridetrackermobdev.model.DaoInterface;
import com.jole.ridetrackermobdev.model.RideDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.testing.TestInstallIn;

/**
 * Hilt Module that overwrites the original one to Create a in Memory Database for testing
 */
@Module
@TestInstallIn(
        components = SingletonComponent.class,
        replaces = DaoModule.class
)
public class DaoModuleMock
{

    @Singleton
    @Provides
    public DaoInterface provideDaoInterface(RideDatabase rideDb)
    {
        return rideDb.daoInterface();
    }


    @Provides
    @Singleton
    public RideDatabase provideRideDb (@ApplicationContext Context context)
    {
        return Room.inMemoryDatabaseBuilder(
                context,
                RideDatabase.class)
                .allowMainThreadQueries()
                .build();
    }
}


    //RideDatabase db = Room.databaseBuilder(getApplicationContext(),RideDatabase.class, "rideDb").build();