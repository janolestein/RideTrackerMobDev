package com.jole.ridetrackermobdev.hilt;

import com.jole.ridetrackermobdev.model.DaoInterface;
import com.jole.ridetrackermobdev.model.RideRepository;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.testing.TestInstallIn;

@Module
@TestInstallIn(
        components = SingletonComponent.class,
        replaces = RideRepositoryModule.class
)
public class RideRepositoryModuleMock
{
    @Singleton
    @Provides
    public RideRepository bindRideRepository(DaoInterface rideDao)
    {
        return Mockito.mock(RideRepository.class);
    }

}
