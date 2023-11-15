package com.jole.ridetrackermobdev.hilt;

import androidx.lifecycle.ViewModel;

import com.jole.ridetrackermobdev.model.DaoInterface;
import com.jole.ridetrackermobdev.model.RideRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RideRepositoryModule
{
    @Singleton
    @Provides
    public RideRepository provideRideRepositoryModule(DaoInterface rideDao){
        return new RideRepository(rideDao);
    }
}
