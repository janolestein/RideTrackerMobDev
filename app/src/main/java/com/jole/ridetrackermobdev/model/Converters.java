package com.jole.ridetrackermobdev.model;

import androidx.room.TypeConverter;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.osmdroid.util.GeoPoint;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Converters for the Rooms Database
 */
public class Converters
{
    /**
     * Converts a String to a List of GeoPoints
     * @param value to be Converted
     * @return List of GeoPoints
     */
    @TypeConverter
    public static List<GeoPoint> fromString(String value) {
        Type listType = new TypeToken<ArrayList<GeoPoint>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    /**
     * Converts a List of GeoPoints to String
     * @param list to be Converted
     * @return String to saved in a Database
     */
    @TypeConverter
    public static String fromArrayList(List<GeoPoint> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    /**
     * Converts a String to a GeoPoint
     * @param data String to be converted
     * @return a converted GeoPoint
     */
    @TypeConverter
    public static GeoPoint stringToGeoPoint(String data) {
        return new Gson().fromJson(data, GeoPoint.class);
    }

    /**
     * Converts a GeoPoint to a String
     * @param geoPoint to be Converted
     * @return String that got converted
     */
    @TypeConverter
    public static String geoPointToString(GeoPoint geoPoint) {
        return new Gson().toJson(geoPoint);
    }
}
