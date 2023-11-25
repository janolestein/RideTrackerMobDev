package com.jole.ridetrackermobdev.ui;

import static androidx.test.espresso.Espresso.onView;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;

@LargeTest
public class MapCurrentFragmentEspressoTest {

    FragmentScenario<MapCurrentFragment> fragmentScenario;

    @Test
    public void testIfMapIsDisplayd() {
        fragmentScenario = FragmentScenario.launchInContainer(MapCurrentFragment.class, null);

    }
}
