package com.jole.ridetrackermobdev.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;

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
    public void testIfLayoutAndMapAreDisplayed() {
        fragmentScenario = FragmentScenario.launchInContainer(MapCurrentFragment.class, null);

        onView(withId(16908290)).check(matches(isDisplayed()));
        //onView(allOf(withId(-1), withText("mapView"))).check(matches(isDisplayed()));
        onView(withTagValue(is((Object) "mapView"))).check(matches(isDisplayed()));

    }
}
