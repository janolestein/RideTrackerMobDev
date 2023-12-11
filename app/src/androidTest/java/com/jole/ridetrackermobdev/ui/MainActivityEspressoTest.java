package com.jole.ridetrackermobdev.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.jole.ridetrackermobdev.ui.HiltContainer.launchFragmentInHiltContainer;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;

import android.Manifest;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;

import com.jole.ridetrackermobdev.HiltTestActivity;
import com.jole.ridetrackermobdev.R;
import com.jole.ridetrackermobdev.controller.MainFragmentsViewModel;
import com.jole.ridetrackermobdev.controller.RideDetailViewModel;
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
public class MainActivityEspressoTest {


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
    LiveData<double[]> uiStateMock = new MutableLiveData<>(new double[]{345D, 475698D, 567567D});
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);
    @Rule
    public GrantPermissionRule permissionRule1 = GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION);
    @Rule
    public GrantPermissionRule permissionRule2 = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE);

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

        ride1 = new Ride("Test1", "This is a Example Ride Description", LocalDate.now().toString(), 60, 25.6, 1.45,
                "https://static-maps.alltrails.com/production/at-map/132570830/v1-trail-england-northumberland-holy-island-bicycle-ride-at-map-132570830-1689185982-327w203h-en-US-i-2-style_3.png", gPoints);
        ride2 = new Ride("Test2", "This is a test", LocalDate.now().toString(), 60, 25.6, 1.45,
                "https://static-maps.alltrails.com/production/at-map/132570830/v1-trail-england-northumberland-holy-island-bicycle-ride-at-map-132570830-1689185982-327w203h-en-US-i-2-style_3.png", gPoints);
        ride3 = new Ride("Test3", "This is a test", LocalDate.now().toString(), 60, 25.6, 1.45,
                "https://static-maps.alltrails.com/production/at-map/132570830/v1-trail-england-northumberland-holy-island-bicycle-ride-at-map-132570830-1689185982-327w203h-en-US-i-2-style_3.png", gPoints);
        ride4 = new Ride("Test4", "This is a test", LocalDate.now().toString(), 60, 25.6, 1.45,
                "https://static-maps.alltrails.com/production/at-map/132570830/v1-trail-england-northumberland-holy-island-bicycle-ride-at-map-132570830-1689185982-327w203h-en-US-i-2-style_3.png", gPoints);
        ride5 = new Ride("Test5", "This is a test", LocalDate.now().toString(), 60, 25.6, 1.45,
                "https://static-maps.alltrails.com/production/at-map/132570830/v1-trail-england-northumberland-holy-island-bicycle-ride-at-map-132570830-1689185982-327w203h-en-US-i-2-style_3.png", gPoints);
        ride6 = new Ride("Test6", "This is a test", LocalDate.now().toString(), 60, 25.6, 1.45,
                "https://static-maps.alltrails.com/production/at-map/132570830/v1-trail-england-northumberland-holy-island-bicycle-ride-at-map-132570830-1689185982-327w203h-en-US-i-2-style_3.png", gPoints);
        ride7 = new Ride("Test7", "This is a test", LocalDate.now().toString(), 60, 25.6, 1.45,
                "https://static-maps.alltrails.com/production/at-map/132570830/v1-trail-england-northumberland-holy-island-bicycle-ride-at-map-132570830-1689185982-327w203h-en-US-i-2-style_3.png", gPoints);
        ride8 = new Ride("Test8", "This is a test", LocalDate.now().toString(), 60, 25.6, 1.45,
                "https://static-maps.alltrails.com/production/at-map/132570830/v1-trail-england-northumberland-holy-island-bicycle-ride-at-map-132570830-1689185982-327w203h-en-US-i-2-style_3.png", gPoints);


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
        Mockito.when(vModel.getUiState()).thenReturn(uiStateMock);

    }

    @Test
    public void testMainNavigation(){
        onView(withId(R.id.nav_map)).check(matches(isDisplayed()));
        onView(withId(R.id.nav_record)).check(matches(isDisplayed()));
        onView(withId(R.id.nav_rides)).check(matches(isDisplayed()));
        onView(withId(16908290)).check(matches(isDisplayed()));
        onView(withTagValue(is((Object) "mapView"))).check(matches(isDisplayed()));

        onView(withId(R.id.nav_record)).perform(click());
        onView(withId(R.id.tvTimeTitle)).check(matches(withText("Time")));
        onView(withId(R.id.tvFragTitelTraining)).check(matches(withText("Record Ride")));
        onView(withId(R.id.tvDistanceTitel)).check(matches(withText("Distance")));

        onView(withId(R.id.nav_rides)).perform(click());

        onView(ViewMatchers.withId(R.id.recViewAllRides))
                .perform(RecyclerViewActions.scrollTo(
                        hasDescendant(withText("Test3"))
                ));
        onView(ViewMatchers.withId(R.id.recViewAllRides))
                .perform(RecyclerViewActions.scrollTo(
                        hasDescendant(withText("Test5"))
                ));
        onView(ViewMatchers.withId(R.id.recViewAllRides))
                .perform(RecyclerViewActions.scrollTo(
                        hasDescendant(withText("Test8"))
                ));

        pressBack();
        onView(withId(R.id.tvTimeTitle)).check(matches(withText("Time")));
        onView(withId(R.id.tvFragTitelTraining)).check(matches(withText("Record Ride")));
        onView(withId(R.id.tvDistanceTitel)).check(matches(withText("Distance")));

        pressBack();
        onView(withId(16908290)).check(matches(isDisplayed()));
        onView(withTagValue(is((Object) "mapView"))).check(matches(isDisplayed()));
    }
}


