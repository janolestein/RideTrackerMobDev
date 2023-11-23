package com.jole.ridetrackermobdev;

import static org.junit.Assert.assertEquals;

import com.jole.ridetrackermobdev.controller.Util;

import org.junit.Test;
import org.osmdroid.util.GeoPoint;

public class UtilTest
{
    @Test
    public void testGeoPointDistanceCalculation()
    {
        GeoPoint g1 = new GeoPoint(52.0, 13.0);
        GeoPoint g2 = new GeoPoint(53.0, 14.0);

        double result = Util.distanceBetweenTwoGeoPoints(g1, g2);

        assertEquals(130.17536520511433, result, 0);

        g1 = new GeoPoint(52.531538983179104, 13.469196574111619);
        g2 = new GeoPoint(52.31410949749011, 4.857090867172472);

        result = Util.distanceBetweenTwoGeoPoints(g1, g2);
        assertEquals(584.1393197781291, result, 0);

    }
}
