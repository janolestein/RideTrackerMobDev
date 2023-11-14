package com.jole.ridetrackermobdev.model;

import androidx.room.TypeConverter;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.osmdroid.util.GeoPoint;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Converters
{
    @TypeConverter
    public static List<GeoPoint> fromString(String value) {
        Type listType = new TypeToken<ArrayList<GeoPoint>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(List<GeoPoint> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static GeoPoint stringToGeoPoint(String data) {
        return new Gson().fromJson(data, GeoPoint.class);
    }

    @TypeConverter
    public static String geoPointToString(GeoPoint geoPoint) {
        return new Gson().toJson(geoPoint);
    }
}
