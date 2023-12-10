package com.jole.ridetrackermobdev.hilt;

import android.content.Context;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.time.Clock;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

/**
 * Hilt Module that Provides a Java.Util.Clock Object
 */
@Module
@InstallIn(SingletonComponent.class)
public class ClockModule
{
    @Provides
    public Clock provideClock()
    {
        return Clock.systemDefaultZone();
    }
}
