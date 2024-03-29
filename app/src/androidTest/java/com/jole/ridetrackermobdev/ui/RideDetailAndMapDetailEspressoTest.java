package com.jole.ridetrackermobdev.ui;

import static androidx.test.core.app.ActivityScenario.launch;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.jole.ridetrackermobdev.ui.HiltContainer.launchFragmentInHiltContainer;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;

import android.content.Intent;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import com.jole.ridetrackermobdev.R;
import com.jole.ridetrackermobdev.controller.MainFragmentsViewModel;
import com.jole.ridetrackermobdev.controller.RideDetailViewModel;
import com.jole.ridetrackermobdev.model.Ride;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.osmdroid.util.GeoPoint;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import dagger.hilt.android.testing.BindValue;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
@HiltAndroidTest
@LargeTest
public class RideDetailAndMapDetailEspressoTest {

    @Rule(order = 0)
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);
    @BindValue
    RideDetailViewModel vModelDetail = mock(RideDetailViewModel.class);

    @Rule(order = 3)
    public InstantTaskExecutorRule instantTaskExecutorRule =
            new InstantTaskExecutorRule();
    List<Ride> rideList;
    List<GeoPoint> gPoints;
    Ride ride1, ride2, ride3, ride4, ride5, ride6, ride7, ride8;

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

        ride1 = new Ride("Test1",  LocalDate.now().toString(), 60, 25.6, 567567D, gPoints);
        ride2 = new Ride("Test2",  LocalDate.now().toString(), 60, 25.6, 567567D, gPoints);
        ride3 = new Ride("Test3",  LocalDate.now().toString(), 60, 25.6, 567567D, gPoints);
        ride4 = new Ride("Test4",  LocalDate.now().toString(), 60, 25.6, 567567D, gPoints);
        ride5 = new Ride("Test5",  LocalDate.now().toString(), 60, 25.6, 567567D, gPoints);
        ride6 = new Ride("Test6",  LocalDate.now().toString(), 60, 25.6, 567567D, gPoints);
        ride7 = new Ride("Test7",  LocalDate.now().toString(), 60, 25.6, 567567D, gPoints);
        ride8 = new Ride("Test8",  LocalDate.now().toString(), 60, 25.6, 567567D, gPoints);


        rideList = new LinkedList<>();
        rideList.add(ride1);
        rideList.add(ride2);
        rideList.add(ride3);
        rideList.add(ride4);
        rideList.add(ride5);
        rideList.add(ride6);
        rideList.add(ride7);
        rideList.add(ride8);

        Mockito.when(vModelDetail.findRideById(anyInt())).thenAnswer(i -> Optional.of(rideList.get((Integer) i.getArguments()[0])));

    }

    @Test
    public void testIfViewsAreDisplayed(){
        Intent intent1 = new Intent(ApplicationProvider.getApplicationContext(), RideDetailActivity.class);
        intent1.putExtra("RideId", 2);
        ActivityScenario<RideDetailActivity> activityScenario1 = launch(intent1);

        onView(withId(R.id.tvRideTitelDetail)).check(matches(withText("Test3")));
        onView(withId(R.id.tvDateDetail)).check(matches(withText(LocalDate.now().toString())));
        onView(withId(R.id.tvTimeVar)).check(matches(withText("0h, 9min, 27sec")));

        Intent intent2 = new Intent(ApplicationProvider.getApplicationContext(), RideDetailActivity.class);
        intent2.putExtra("RideId", 7);
        ActivityScenario<RideDetailActivity> activityScenario2 = launch(intent2);

        onView(withId(R.id.tvRideTitelDetail)).check(matches(withText("Test8")));
        onView(withId(R.id.tvDateDetail)).check(matches(withText(LocalDate.now().toString())));
        onView(withId(R.id.tvTimeVar)).check(matches(withText("0h, 9min, 27sec")));
    }

    @Test
    public void testIfMapOpens(){
        Intent intent1 = new Intent(ApplicationProvider.getApplicationContext(), RideDetailActivity.class);
        intent1.putExtra("RideId", 2);
        ActivityScenario<RideDetailActivity> activityScenario1 = launch(intent1);

        onView(withId(R.id.btnViewMap)).perform(click());

        onView(withTagValue(is((Object) "mapView"))).check(matches(isDisplayed()));
        pressBack();
        onView(withId(R.id.tvRideTitelDetail)).check(matches(withText("Test3")));
        onView(withId(R.id.tvDateDetail)).check(matches(withText(LocalDate.now().toString())));
        onView(withId(R.id.tvTimeVar)).check(matches(withText("0h, 9min, 27sec")));
    }
}
