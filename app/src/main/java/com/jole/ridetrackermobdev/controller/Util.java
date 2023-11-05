package com.jole.ridetrackermobdev.controller;

import org.osmdroid.util.GeoPoint;

public class Util {

    public static double distanceBetweenTwoGeoPoints(GeoPoint gp1, GeoPoint gp2)
    {

        Double lon1 = Math.toRadians(gp1.getLongitude());
        Double lon2 = Math.toRadians(gp2.getLongitude());
        Double lat1 = Math.toRadians(gp1.getLatitude());
        Double lat2 = Math.toRadians(gp2.getLatitude());

        double dlon = Math.abs(lon2 - lon1);
        double dlat = Math.abs(lat2 - lat1);
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        double r = 6371;

        // calculate the result
        return(c * r);
    }
}
