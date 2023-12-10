package com.jole.ridetrackermobdev.hilt;

import androidx.lifecycle.ViewModel;

import com.jole.ridetrackermobdev.model.DaoInterface;
import com.jole.ridetrackermobdev.model.RideRepository;
import com.jole.ridetrackermobdev.model.RideRepositoryInterface;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.components.SingletonComponent;

/**
 * Hilt Module that Provides a instance of a RideRepositoryInterface
 */
@Module
@InstallIn(SingletonComponent.class)
public class RideRepositoryModule
{
    @Singleton
    @Provides
    public RideRepositoryInterface bindRideRepository(DaoInterface rideDao)
    {
        return new RideRepository(rideDao);
    }

}
