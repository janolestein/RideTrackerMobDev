package com.jole.ridetrackermobdev.hilt;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.jole.ridetrackermobdev.model.DaoInterface;
import com.jole.ridetrackermobdev.model.Ride;

import com.jole.ridetrackermobdev.model.RideDatabase;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DaoModule
{

    @Provides
    public DaoInterface provideDaoInterface(RideDatabase rideDb)
    {
        return rideDb.daoInterface();
    }


    @Provides
    @Singleton
    public RideDatabase provideRideDb (@ApplicationContext Context context)
    {
        return Room.databaseBuilder(
                context,
                RideDatabase.class,
                "rideDb")
                .build();

    }
}


    //RideDatabase db = Room.databaseBuilder(getApplicationContext(),RideDatabase.class, "rideDb").build();