package com.jole.ridetrackermobdev.endtoend;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;

import android.Manifest;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;

import com.jole.ridetrackermobdev.R;
import com.jole.ridetrackermobdev.controller.RecordRideService;
import com.jole.ridetrackermobdev.controller.Util;
import com.jole.ridetrackermobdev.hilt.RideRepositoryModule;
import com.jole.ridetrackermobdev.model.DaoInterface;
import com.jole.ridetrackermobdev.model.Ride;
import com.jole.ridetrackermobdev.model.RideRepository;
import com.jole.ridetrackermobdev.model.RideRepositoryInterface;
import com.jole.ridetrackermobdev.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.osmdroid.util.GeoPoint;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.UninstallModules;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.testing.TestInstallIn;


@HiltAndroidTest
@LargeTest
public class EndToEndIntegrationTest
{

    @Rule(order = 1)
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule(order = 0)
    public InstantTaskExecutorRule instantTaskExecutorRule =
            new InstantTaskExecutorRule();

    @Rule(order = 5)
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Rule(order = 2)
    public GrantPermissionRule permissionRule1 = GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION);
    @Rule(order = 3)
    public GrantPermissionRule permissionRule2 = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    @Rule(order = 4)
    public GrantPermissionRule permissionRule3 = GrantPermissionRule.grant(Manifest.permission.POST_NOTIFICATIONS);

    String formattedDate;
    String title;

    private CountDownLatch lock = new CountDownLatch(4);
    @Before
    public void setUp() throws InterruptedException
    {
        hiltRule.inject();

        LocalDate today = LocalDate.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
        formattedDate = today.format(pattern);
        title = today.getDayOfWeek().getDisplayName(TextStyle.FULL_STANDALONE, Locale.US) + " " + Util.getTimeOfDay() + " Ride";

    }

    /**
     * Runs trough the complete App and tests if all parts are working together Correctly
     * @throws InterruptedException
     */
    @Test
    public void integrationTest() throws InterruptedException
    {
        onView(withId(R.id.nav_map)).check(matches(isDisplayed()));
        onView(withId(R.id.nav_record)).check(matches(isDisplayed()));
        onView(withId(R.id.nav_rides)).check(matches(isDisplayed()));
        onView(withTagValue(is((Object) "mapView"))).check(matches(isDisplayed()));

        onView(withId(R.id.nav_record)).perform(click());
        onView(withId(R.id.tvTimeTitle)).check(matches(withText("Time")));
        onView(withId(R.id.tvFragTitelTraining)).check(matches(withText("Record Ride")));
        onView(withId(R.id.tvDistanceTitel)).check(matches(withText("Distance")));
        onView(withId(R.id.tvDistanceVar)).check(matches(withText("0,00 km")));
        onView(withId(R.id.tvElapsedTimeVar)).check(matches(withText("0h, 0min, 0sec")));
        onView(withId(R.id.tvAverageSpeedVar)).check(matches(withText("0,00 km/h")));

        assertFalse(RecordRideService.isRunning);
        onView(withId(R.id.btnStartRecord)).perform(click());
        assertTrue(RecordRideService.isRunning);

        lock.countDown();
        lock.await(5000, TimeUnit.MILLISECONDS);

        onView(withId(R.id.tvElapsedTimeVar)).check(matches(not((withText("0.0")))));
        onView(withId(R.id.btnStopRecord)).perform(click());
        assertFalse(RecordRideService.isRunning);
        onView(withId(R.id.tvDistanceVar)).check(matches(withText("0,00 km")));

        onView(withId(R.id.nav_rides)).perform(click());
        onView(ViewMatchers.withId(R.id.recViewAllRides))
                .perform(RecyclerViewActions.scrollTo(
                        hasDescendant(withText(formattedDate))
                ));

        onView(withId(R.id.recViewAllRides)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.tvRideTitelDetail)).check(matches(withText(title)));
        onView(withId(R.id.tvDateDetail)).check(matches(withText(formattedDate)));
        onView(withId(R.id.tvTimeVar)).check(matches(not(withText("0h, 0min, 0sec"))));

        onView(withId(R.id.btnViewMap)).perform(click());

        onView(withTagValue(is((Object) "mapView"))).check(matches(isDisplayed()));
        //Starts checking in reverse
        pressBack();
        onView(withId(R.id.tvRideTitelDetail)).check(matches(withText(title)));
        onView(withId(R.id.tvDateDetail)).check(matches(withText(formattedDate)));
        onView(withId(R.id.tvTimeVar)).check(matches(not(withText("0dh, 0min, 0sec"))));

        pressBack();
        onView(ViewMatchers.withId(R.id.recViewAllRides))
                .perform(RecyclerViewActions.scrollTo(
                        hasDescendant(withText(formattedDate))
                ));

        pressBack();
        onView(withId(R.id.tvFragTitelTraining)).check(matches(withText("Record Ride")));
        onView(withId(R.id.tvDistanceTitel)).check(matches(withText("Distance")));
        onView(withId(R.id.tvDistanceVar)).check(matches(withText("0,00 km")));
        onView(withId(R.id.tvElapsedTimeVar)).check(matches(withText("0h, 0min, 0sec")));
        onView(withId(R.id.tvAverageSpeedVar)).check(matches(withText("0,00 km/h")));

        pressBack();
        onView(withTagValue(is((Object) "mapView"))).check(matches(isDisplayed()));
    }
}
