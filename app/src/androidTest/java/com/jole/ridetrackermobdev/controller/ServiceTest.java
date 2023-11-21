package com.jole.ridetrackermobdev.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Intent;

import androidx.test.InstrumentationRegistry;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ServiceTestRule;

import com.jole.ridetrackermobdev.controller.RecordRideService;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeoutException;

@RunWith(AndroidJUnit4.class)
public class ServiceTest
{

    @Rule
    public final ServiceTestRule mServiceRule = new ServiceTestRule();

    @Test
    public void testServiceStarted() throws TimeoutException
    {
        // Create the service Intent.
        Intent serviceIntent =
                new Intent(ApplicationProvider.getApplicationContext(),
                        RecordRideService.class);
        assertFalse(RecordRideService.isRunning);
        mServiceRule.startService(serviceIntent);
        assertTrue(RecordRideService.isRunning);




    }
}
