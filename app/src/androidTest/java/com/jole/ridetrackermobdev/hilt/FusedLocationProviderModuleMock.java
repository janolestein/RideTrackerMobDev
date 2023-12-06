package com.jole.ridetrackermobdev.hilt;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ServiceComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.testing.TestInstallIn;

@Module
@TestInstallIn(
        components = SingletonComponent.class,
        replaces = FusedLocationProviderModule.class
)
public class FusedLocationProviderModuleMock
{
    @Singleton
    @Provides
    public FusedLocationProviderClient provideFusedLocationProvider(@ApplicationContext Context context)
    {
//       return LocationServices.getFusedLocationProviderClient(context);
       FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(context);
       return client;
    }
}

