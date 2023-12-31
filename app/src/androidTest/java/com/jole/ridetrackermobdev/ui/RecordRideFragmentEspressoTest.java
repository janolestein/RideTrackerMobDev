package com.jole.ridetrackermobdev.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static com.jole.ridetrackermobdev.ui.HiltContainer.launchFragmentInHiltContainer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.test.filters.LargeTest;


import com.jole.ridetrackermobdev.HiltTestActivity;
import com.jole.ridetrackermobdev.R;
import com.jole.ridetrackermobdev.controller.MainFragmentsViewModel;
import com.jole.ridetrackermobdev.controller.RecordRideService;
import com.jole.ridetrackermobdev.hilt.RideRepositoryModule;
import com.jole.ridetrackermobdev.model.DaoInterface;
import com.jole.ridetrackermobdev.model.RideRepository;
import com.jole.ridetrackermobdev.model.RideRepositoryInterface;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.testing.BindValue;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.UninstallModules;
import dagger.hilt.components.SingletonComponent;

@LargeTest
@HiltAndroidTest
public class RecordRideFragmentEspressoTest {

    @Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @BindValue
    MainFragmentsViewModel vModel = mock(MainFragmentsViewModel.class);

    LiveData<double[]> uiStateMock = new MutableLiveData<>(new double[]{345D, 475698D, 567567D});



    @Before
    public void setUp(){
        Mockito.when(vModel.getUiState()).thenReturn(uiStateMock);
        launchFragmentInHiltContainer(HiltTestActivity.class, RecordRideFragment.class);
    }
    @Test
    public void testIfViewsAreDisplayed() {
        onView(withId(R.id.tvTimeTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.tvFragTitelTraining)).check(matches(isDisplayed()));
        onView(withId(R.id.tvDistanceTitel)).check(matches(isDisplayed()));
        onView(withId(R.id.tvElapsedTimeVar)).check(matches(isDisplayed()));
        onView(withId(R.id.tvDistanceVar)).check(matches(isDisplayed()));
        onView(withId(R.id.tvAverageSpeedVar)).check(matches(isDisplayed()));

        onView(withId(R.id.btnStartRecord)).check(matches(isDisplayed()));
        onView(withId(R.id.btnStartRecord)).perform(click());
        onView(withId(R.id.btnStopRecord)).check(matches(isDisplayed()));
        onView(withId(R.id.btnStopRecord)).perform(click());
        onView(withId(R.id.btnStartRecord)).check(matches(isDisplayed()));

    }

    @Test
    public void testIfContentInViewsIsCorrect(){
        onView(withId(R.id.tvTimeTitle)).check(matches(withText("Time")));
        onView(withId(R.id.tvFragTitelTraining)).check(matches(withText("Record Ride")));
        onView(withId(R.id.tvDistanceTitel)).check(matches(withText("Distance")));

        onView(withId(R.id.tvDistanceVar)).check(matches(withText("345,00 km")));
        onView(withId(R.id.tvElapsedTimeVar)).check(matches(withText("0h, 7min, 55sec")));
        onView(withId(R.id.tvAverageSpeedVar)).check(matches(withText("567567,00 km/h")));

        onView(withId(R.id.btnStartRecord)).check(matches(withText("Start")));
        onView(withId(R.id.btnStartRecord)).perform(click());
        onView(withId(R.id.btnStopRecord)).check(matches(withText("Stop")));
    }

    @Test
    public void testIfButtonStartsService(){
        assertFalse(RecordRideService.isRunning);
        onView(withId(R.id.btnStartRecord)).perform(click());
        assertTrue(RecordRideService.isRunning);
        onView(withId(R.id.btnStopRecord)).perform(click());
        assertFalse(RecordRideService.isRunning);
    }
}
