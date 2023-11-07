package com.jole.ridetrackermobdev.model;

import org.osmdroid.util.GeoPoint;

import java.time.LocalDate;
import java.util.List;

public class Ride {
    private int id;
    private static int numberOfRides = 1;
    private String name;
    private String description;
    private LocalDate date;
    private double rideLengthKm;
    private double averageSpeed;
    private double totalRideTime;
    private String imgUrl;
    private List<GeoPoint> geoPoints;


    public Ride(String name, String description, LocalDate date, double rideLengthKm, double averageSpeed, double totalRideTime, String imgUrl, List<GeoPoint> geoPoints) {
        this.id = numberOfRides;
        this.name = name;
        this.description = description;
        this.date = date;
        this.rideLengthKm = rideLengthKm;
        this.averageSpeed = averageSpeed;
        this.totalRideTime = totalRideTime;
        this.imgUrl = imgUrl;
        this.geoPoints = geoPoints;
        numberOfRides++;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public static int getNumberOfRides()
    {
        return numberOfRides;
    }
    public List<GeoPoint> getGeoPoints()
    {
        return geoPoints;
    }
}
