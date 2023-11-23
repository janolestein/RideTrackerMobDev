package com.jole.ridetrackermobdev.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ServiceTestRule;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.jole.ridetrackermobdev.controller.RecordRideService;
import com.jole.ridetrackermobdev.model.RideRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;

@HiltAndroidTest
public class ServiceTest
{
    @Inject
    FusedLocationProviderClient client;
    @Inject
    RideRepository repo;
    double[] liveDataArr;

    @Rule
    public final ServiceTestRule mServiceRule = new ServiceTestRule();
    @Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule =
            new InstantTaskExecutorRule();

    @Before
    public void setUp(){
        hiltRule.inject();
        Location location = new Location(LocationManager.GPS_PROVIDER);
        location.setLatitude(52.0);
        location.setLongitude(13.0);
        client.setMockLocation(location);
    }

    @Test
    public void testServiceStarted() throws TimeoutException
    {
        // Create the service Intent.
        assertFalse(RecordRideService.isRunning);
        mServiceRule.startService(
                new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), RecordRideService.class));
        assertTrue(RecordRideService.isRunning);

        Thread t = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(10000);
                } catch (InterruptedException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });
        t.start();
        try
        {
            t.join();
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
        Location location = new Location(LocationManager.GPS_PROVIDER);
        location.setLatitude(55.0);
        location.setLongitude(13.0);
        client.setMockLocation(location);

        Location location1 = new Location(LocationManager.GPS_PROVIDER);
        location1.setLatitude(52.0);
        location1.setLongitude(14.0);
        client.setMockLocation(location1);

        Location location2 = new Location(LocationManager.GPS_PROVIDER);
        location2.setLatitude(52.0);
        location2.setLongitude(17.0);
        client.setMockLocation(location2);



        repo.getRideServiceUiState().observeForever(new Observer<double[]>()
        {
            @Override
            public void onChanged(double[] doubles)
            {
                liveDataArr = doubles;
            }
        });
        Thread t2 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(5000);
                } catch (InterruptedException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });
        t2.start();
        try
        {
            t2.join();
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
        assertTrue(liveDataArr[1] == 0);


    }
}

