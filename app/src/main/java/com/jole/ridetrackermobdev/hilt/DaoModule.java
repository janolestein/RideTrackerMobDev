package com.jole.ridetrackermobdev.hilt;

import androidx.fragment.app.Fragment;

import com.jole.ridetrackermobdev.model.DaoInterface;
import com.jole.ridetrackermobdev.model.RideDao;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class DaoModule
{

    @Binds
    public abstract DaoInterface bindDaoInterface(
            RideDao rideDao
    );
}
