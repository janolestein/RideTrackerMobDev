package com.jole.ridetrackermobdev.hilt;

import android.content.Context;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

/**
 * Hilt Module for a Local Broadcast Manager
 * No Longer in Use in favor of the Repository and ViewModel way
 */
@Deprecated
@Module
@InstallIn(SingletonComponent.class)
public class BroadcasterModule
{
    @Provides
    public LocalBroadcastManager provideLocalBroadcaster(@ApplicationContext Context context)
    {
        return LocalBroadcastManager.getInstance(context);
    }
}
