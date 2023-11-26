package com.jole.ridetrackermobdev.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import com.jole.ridetrackermobdev.R;

import org.junit.Rule;
import org.junit.Test;

@LargeTest
public class MapCurrentFragmentEspressoTest {

    FragmentScenario<MapCurrentFragment> fragmentScenario;

    @Test
    public void testIfMapIsDisplayd() {
        fragmentScenario = FragmentScenario.launchInContainer(MapCurrentFragment.class, null);

        onView(withId(R.id.tvTimeTitle)).check(matches(isDisplayed()));

    }
}
