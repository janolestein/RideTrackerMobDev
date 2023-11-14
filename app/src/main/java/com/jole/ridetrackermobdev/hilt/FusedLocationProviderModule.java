package com.jole.ridetrackermobdev.hilt;

import android.content.Context;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ServiceComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

@Module
@InstallIn(ServiceComponent.class)
public class FusedLocationProviderModule
{
    @Provides
    public FusedLocationProviderClient provideFusedLocationProvider(@ApplicationContext Context context)
    {
        return LocationServices.getFusedLocationProviderClient(context);
    }
}

