package com.jole.ridetrackermobdev.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.rule.ServiceTestRule;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationResult;
import com.jole.ridetrackermobdev.hilt.RideRepositoryModule;
import com.jole.ridetrackermobdev.model.DaoInterface;
import com.jole.ridetrackermobdev.model.RideRepository;
import com.jole.ridetrackermobdev.model.RideRepositoryInterface;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.Clock;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.UninstallModules;
import dagger.hilt.components.SingletonComponent;

@UninstallModules(RideRepositoryModule.class)
@HiltAndroidTest
public class ServiceTest
{
    @Module
    @InstallIn(SingletonComponent.class)
    public class RideRepositoryModuleMock
    {
        @Singleton
        @Provides
        public RideRepositoryInterface bindRideRepository(DaoInterface rideDao)
        {
            return Mockito.mock(RideRepository.class);
        }

    }

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
    Clock clock = mock(Clock.class);
    @Inject
    RideRepository repoForMockTest;

    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        hiltRule.inject();
        Mockito.when(clock.millis()).thenReturn(100000L);
    }

    @Test
    public void testCallback(){
        RecordRideService service = new RecordRideService((RideRepository) repo);
        service.registerLocationCallback(clock);
        Mockito.when(clock.millis()).thenReturn(500000L);

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
        assertEquals(capture[1], 400000.0, 0);
        assertEquals(capture[2], 54312.901075038884, 0);

        Mockito.when(clock.millis()).thenReturn(700000L);
        service.locationCallback.onLocationResult(locationResult);

        Mockito.verify(repo, times(3)).setRideServiceUiState(captor.capture());

        capture = captor.getValue();
        assertEquals(capture[0], 18104.300358346292, 0);
        assertEquals(capture[1], 600000.0, 0);
        assertEquals(capture[2], 108625.80215007775, 0);

        service.locationCallback.onLocationResult(locationResult);

        Mockito.verify(repo, times(5)).setRideServiceUiState(any());

    }

    @Test
    public void testServiceStarts() throws TimeoutException
    {

        assertFalse(RecordRideService.isRunning);
        mServiceRule.startService(
                new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), RecordRideService.class));
        assertTrue(RecordRideService.isRunning);

    }

    /**
     * Test Service with LocationProvider in Mock mode
     * @throws TimeoutException
     */
    @Test
    public void testMockMode() throws TimeoutException
    {

        Location location = new Location("flp");
        location.setLatitude(52.0);
        location.setLongitude(13.0);
        Location location2 = new Location("flp");
        location.setLatitude(53.0);
        location.setLongitude(14.0);
        client.setMockMode(true);
        client.setMockLocation(location);
        client.getLastLocation();

        assertFalse(RecordRideService.isRunning);
        mServiceRule.startService(
                new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), RecordRideService.class));
        assertTrue(RecordRideService.isRunning);
        client.setMockLocation(location2);
        client.getLastLocation();
        client.setMockLocation(location2);
        client.getLastLocation();
        client.setMockLocation(location2);
        client.getLastLocation();
        client.setMockLocation(location2);
        client.getLastLocation();
        client.setMockLocation(location2);
        client.getLastLocation();
        client.setMockLocation(location2);
        client.getLastLocation();
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
        client.setMockLocation(location2);
        client.getLastLocation();
        client.setMockLocation(location2);
        client.getLastLocation();
        client.setMockLocation(location2);
        client.getLastLocation();
        client.setMockLocation(location2);
        client.getLastLocation();
        client.setMockLocation(location2);
        client.getLastLocation();
        client.setMockLocation(location2);
        client.getLastLocation();
        repoForMockTest.getRideServiceUiState().observeForever(new Observer<double[]>()
        {
            @Override
            public void onChanged(double[] doubles)
            {
                Log.v("ServiceTest", Double.toString(doubles[0]));
            }
        });


    }
}

