package com.jole.ridetrackermobdev.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.jole.ridetrackermobdev.ui.HiltContainer.launchFragmentInHiltContainer;
import static org.mockito.Mockito.mock;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.LargeTest;

import com.jole.ridetrackermobdev.HiltTestActivity;
import com.jole.ridetrackermobdev.R;
import com.jole.ridetrackermobdev.controller.MainFragmentsViewModel;
import com.jole.ridetrackermobdev.model.DaoInterface;
import com.jole.ridetrackermobdev.model.Ride;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.osmdroid.util.GeoPoint;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.testing.BindValue;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;

@LargeTest
@HiltAndroidTest
public class TrackedRidesFragmentEspressoTest {

    @Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @BindValue
    MainFragmentsViewModel vModel = mock(MainFragmentsViewModel.class);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule =
            new InstantTaskExecutorRule();

    List<Ride> rideList;

    List<GeoPoint> gPoints;
    @Inject
    DaoInterface dao;

    Ride ride;
    Ride ride2;

    private CountDownLatch lock = new CountDownLatch(4);

    @Before
    public void setUp() throws InterruptedException
    {
        hiltRule.inject();
        gPoints = new LinkedList<>();
        gPoints.add(new GeoPoint(52.458159970620216, 13.527038899381642));
        gPoints.add(new GeoPoint(52.46051831693104, 13.521824258809318));
        gPoints.add(new GeoPoint(52.46187056912226, 13.516528352449457));
        gPoints.add(new GeoPoint(52.46330523870933, 13.510321300073972));
        gPoints.add(new GeoPoint(52.462761066704125, 13.506884073289987));
        gPoints.add(new GeoPoint(52.46080964195072, 13.503933924512413));
        gPoints.add(new GeoPoint(52.46082614030988, 13.501795751994008));
        gPoints.add(new GeoPoint(52.45821491925012, 13.497420340748592));
        gPoints.add(new GeoPoint(52.46126039973983, 13.4932430495305));
        gPoints.add(new GeoPoint(52.46363503504482, 13.489868695841839));
        gPoints.add(new GeoPoint(52.4661139469194, 13.495570882101445));

        ride = new Ride("Wednesday Evening Ride", "This is a Example Ride Description", LocalDate.now().toString(), 60, 25.6, 1.45,
                "https://static-maps.alltrails.com/production/at-map/132570830/v1-trail-england-northumberland-holy-island-bicycle-ride-at-map-132570830-1689185982-327w203h-en-US-i-2-style_3.png", gPoints);
        ride2 = new Ride("Sunday Evening Ride", "This is a test", LocalDate.now().toString(), 60, 25.6, 1.45,
                "https://static-maps.alltrails.com/production/at-map/132570830/v1-trail-england-northumberland-holy-island-bicycle-ride-at-map-132570830-1689185982-327w203h-en-US-i-2-style_3.png", gPoints);


        dao.addNewRide(ride);
        dao.addNewRide(ride);
        dao.addNewRide(ride);
        dao.addNewRide(ride);
        dao.addNewRide(ride);
        dao.addNewRide(ride);
        dao.addNewRide(ride);
        dao.addNewRide(ride);
        dao.addNewRide(ride);
        dao.addNewRide(ride);
        LiveData<List<Ride>> rideList = dao.getAllRidesList();
        lock.countDown();
        lock.await(5000, TimeUnit.MILLISECONDS);
        Mockito.when(vModel.getAllRides()).thenReturn(rideList);

        launchFragmentInHiltContainer(HiltTestActivity.class, TrackedRidesFragment.class);

    }

    @Test
    public void testRecyclerViewPosition(){
        onView(withId(R.id.recViewAllRides)).perform(RecyclerViewActions.scrollToPosition(500));
        onView(withId(R.id.recViewAllRides)).perform(RecyclerViewActions.scrollToPosition(15));
    }

    @Test
    public void testDifferentHolders(){
        onView(withId(R.id.recViewAllRides))
                .check(matches(atPosition(0, hasDescendant(withText("First Element")))));
    }
}
