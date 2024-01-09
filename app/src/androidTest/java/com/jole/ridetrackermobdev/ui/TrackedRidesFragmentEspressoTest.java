package com.jole.ridetrackermobdev.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.jole.ridetrackermobdev.ui.HiltContainer.launchFragmentInHiltContainer;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;

import com.jole.ridetrackermobdev.HiltTestActivity;
import com.jole.ridetrackermobdev.R;
import com.jole.ridetrackermobdev.controller.MainFragmentsViewModel;
import com.jole.ridetrackermobdev.controller.RideDetailViewModel;
import com.jole.ridetrackermobdev.model.DaoInterface;
import com.jole.ridetrackermobdev.model.Ride;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.osmdroid.util.GeoPoint;

import java.time.LocalDate;
import java.util.Collections;
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

    @BindValue
    RideDetailViewModel vModelDetail = mock(RideDetailViewModel.class);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule =
            new InstantTaskExecutorRule();
    List<Ride> rideList;
    List<GeoPoint> gPoints;
    @Inject
    DaoInterface dao;
    Ride ride1, ride2, ride3, ride4, ride5, ride6, ride7, ride8;
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

        ride1 = new Ride("Test1",  LocalDate.now().toString(), 60, 25.6, 1.45, gPoints);
        ride2 = new Ride("Test2",  LocalDate.now().toString(), 60, 25.6, 1.45, gPoints);
        ride3 = new Ride("Test3",  LocalDate.now().toString(), 60, 25.6, 1.45, gPoints);
        ride4 = new Ride("Test4",  LocalDate.now().toString(), 60, 25.6, 1.45, gPoints);
        ride5 = new Ride("Test5",  LocalDate.now().toString(), 60, 25.6, 1.45, gPoints);
        ride6 = new Ride("Test6",  LocalDate.now().toString(), 60, 25.6, 1.45, gPoints);
        ride7 = new Ride("Test7",  LocalDate.now().toString(), 60, 25.6, 1.45, gPoints);
        ride8 = new Ride("Test8",  LocalDate.now().toString(), 60, 25.6, 1.45, gPoints);


        dao.addNewRide(ride1);
        dao.addNewRide(ride2);
        dao.addNewRide(ride3);
        dao.addNewRide(ride4);
        dao.addNewRide(ride5);
        dao.addNewRide(ride6);
        dao.addNewRide(ride7);
        dao.addNewRide(ride8);

        LiveData<List<Ride>> rideList = dao.getAllRidesList();
        lock.countDown();
        lock.await(5000, TimeUnit.MILLISECONDS);

        Mockito.when(vModel.getAllRides()).thenReturn(rideList);
        Mockito.when(vModelDetail.findRideById(anyInt())).thenAnswer(i -> dao.findRideById((Integer) i.getArguments()[0]));

        launchFragmentInHiltContainer(HiltTestActivity.class, TrackedRidesFragment.class);
    }

    @Test
    public void testRecyclerViewPosition() throws InterruptedException
    {
        onView(withId(R.id.recViewAllRides)).perform(RecyclerViewActions.actionOnItemAtPosition(7, click()));
        onView(withId(R.id.tvRideTitelDetail)).check(matches(withText("Test1")));
        pressBack();
        onView(withId(R.id.recViewAllRides));
        onView(withId(R.id.recViewAllRides)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.tvRideTitelDetail)).check(matches(withText("Test8")));
        pressBack();
        onView(withId(R.id.recViewAllRides));
        onView(withId(R.id.recViewAllRides)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        onView(withId(R.id.tvRideTitelDetail)).check(matches(withText("Test5")));
        pressBack();
        onView(withId(R.id.recViewAllRides));
    }

    @Test
    public void testDifferentHolders(){
        onView(ViewMatchers.withId(R.id.recViewAllRides))
                .perform(RecyclerViewActions.scrollTo(
                        hasDescendant(withText("Test6"))
                ));
        onView(ViewMatchers.withId(R.id.recViewAllRides))
                .perform(RecyclerViewActions.scrollTo(
                        hasDescendant(withText("Test5"))
                ));
        onView(ViewMatchers.withId(R.id.recViewAllRides))
                .perform(RecyclerViewActions.scrollTo(
                        hasDescendant(withText("Test1"))
                ));
    }

    @Test(expected = PerformException.class)
    public void testOutOfBoundsThrowsException(){
        onView(ViewMatchers.withId(R.id.recViewAllRides))
                .perform(RecyclerViewActions.scrollTo(
                        hasDescendant(withText("Test15"))
                ));
        onView(withId(R.id.recViewAllRides)).perform(RecyclerViewActions.actionOnItemAtPosition(15, click()));
    }
}
