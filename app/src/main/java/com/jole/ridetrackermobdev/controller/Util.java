package com.jole.ridetrackermobdev.controller;

import org.osmdroid.util.GeoPoint;

import java.time.LocalDate;
import java.util.Calendar;

/**
 * Util Class with different Functions
 */
public class Util {
    /**
     * Calculates the Distance between to GeoPoints
     * @param gp1 GeoPoint one
     * @param gp2 GeoPoint two
     * @return distance as Double
     */
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

        return(c * r);
    }

    /**
     * Gives back a String with the Time of Day
     * @return String
     */
    public static String getTimeOfDay(){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour <= 5 || hour == 23){
            return "Night";
        } else if (hour <= 10) {
            return "Morning";
        } else if (hour <= 15) {
            return "Noon";
        } else if (hour <= 18) {
            return "Afternoon";
        } else if (hour <= 22) {
            return "Evening";
        }
        else {
            return "";
        }
    }
}
