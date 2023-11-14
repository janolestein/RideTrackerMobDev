package com.jole.ridetrackermobdev.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import org.osmdroid.util.GeoPoint;

import java.time.LocalDate;
import java.util.List;
@Entity
public class Ride {
    @PrimaryKey(autoGenerate = true)
    public int id;
    //private static int numberOfRides = 1;
    public String name;
    public String description;
    public String date;
    public double rideLengthKm;
    public double averageSpeed;
    public double totalRideTime;
    public String imgUrl;
    @TypeConverters({Converters.class})
    public List<GeoPoint> geoPoints;


    public Ride(String name, String description, String date, double rideLengthKm, double averageSpeed, double totalRideTime, String imgUrl, List<GeoPoint> geoPoints) {
        //this.id = numberOfRides;
        this.name = name;
        this.description = description;
        this.date = date;
        this.rideLengthKm = rideLengthKm;
        this.averageSpeed = averageSpeed;
        this.totalRideTime = totalRideTime;
        this.imgUrl = imgUrl;
        this.geoPoints = geoPoints;
        //numberOfRides++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date.toString();
    }

    public double getRideLengthKm() {
        return rideLengthKm;
    }

    public void setRideLengthKm(double rideLengthKm) {
        this.rideLengthKm = rideLengthKm;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public double getTotalRideTime() {
        return totalRideTime;
    }

    public void setTotalRideTime(double totalRideTime) {
        this.totalRideTime = totalRideTime;
    }

//    public static int getNumberOfRides()
//    {
//        return numberOfRides;
//    }
    public List<GeoPoint> getGeoPoints()
    {
        return geoPoints;
    }
}
