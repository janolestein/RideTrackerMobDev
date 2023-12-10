package com.jole.ridetrackermobdev.hilt;

import android.app.Application;

import com.jole.ridetrackermobdev.model.DaoInterface;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.HiltAndroidApp;
import dagger.hilt.android.components.ActivityComponent;

/**
 * Hilt Application Class required by Hilt to Function
 */
@HiltAndroidApp
public class HiltApplicationClass extends Application {


}


