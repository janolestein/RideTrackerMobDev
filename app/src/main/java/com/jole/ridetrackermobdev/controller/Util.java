package com.jole.ridetrackermobdev.controller;

import org.osmdroid.util.GeoPoint;

public class Util {

    public static double distanceBetweenTwoGeoPoints(GeoPoint gp1, GeoPoint gp2)
    {

        double lon1 = Math.toRadians(gp1.getLongitude());
        double lon2 = Math.toRadians(gp2.getLongitude());
        double lat1 = Math.toRadians(gp1.getLatitude());
        double lat2 = Math.toRadians(gp2.getLatitude());

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        double r = 6371;

        // calculate the result
        return(c * r);
    }
}
