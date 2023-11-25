package com.jole.ridetrackermobdev.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.rule.ServiceTestRule;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationResult;
import com.jole.ridetrackermobdev.controller.RecordRideService;
import com.jole.ridetrackermobdev.hilt.RideRepositoryModule;
import com.jole.ridetrackermobdev.hilt.RideRepositoryModuleMock;
import com.jole.ridetrackermobdev.model.RideRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.UninstallModules;
import dagger.hilt.components.SingletonComponent;

@HiltAndroidTest
public class ServiceTest
{
    @Inject
    FusedLocationProviderClient client;
    @Mock
    RideRepository repo;
    @Captor
    ArgumentCaptor<double[]> captor;
    double[] liveDataArr;
    @Rule
    public final ServiceTestRule mServiceRule = new ServiceTestRule();
    @Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule =
            new InstantTaskExecutorRule();
    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION);

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        hiltRule.inject();
    }

    @Test
    public void testCallback(){
        RecordRideService service = new RecordRideService(repo);
        service.registerLocationCallback();

        List<Location> locations = new LinkedList<>();
        Location location = new Location("flp");
        location.setLatitude(52.0);
        location.setLongitude(13.0);
        locations.add(location);

        Location location2 = new Location("flp");
        location.setLatitude(53.0);
        location.setLongitude(14.0);
        locations.add(location2);

        LocationResult locationResult = LocationResult.create(locations);

        service.locationCallback.onLocationResult(locationResult);

        Mockito.verify(repo, times(1)).setRideServiceUiState(captor.capture());
        double[] capture = captor.getValue();
        assertEquals(capture[0], 6034.766786115431d, 0);
        assertTrue(capture[1] > 0);
        assertTrue(capture[2] > 0);

        service.locationCallback.onLocationResult(locationResult);

        Mockito.verify(repo, times(3)).setRideServiceUiState(captor.capture());

        capture = captor.getValue();
        assertEquals(capture[0], 18104.300358346292, 0);
        assertTrue(capture[1] > 0);
        assertTrue(capture[2] > 0);

        service.locationCallback.onLocationResult(locationResult);

        Mockito.verify(repo, times(5)).setRideServiceUiState(any());

    }

    @Test
    public void testServiceStarted() throws TimeoutException
    {
        // Create the service Intent.
        assertFalse(RecordRideService.isRunning);
        mServiceRule.startService(
                new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), RecordRideService.class));
        assertTrue(RecordRideService.isRunning);
    }
}

