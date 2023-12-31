package com.jole.ridetrackermobdev.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import org.osmdroid.util.GeoPoint;

import java.time.LocalDate;
import java.util.List;

/**
 * Data Class for a Ride
 * Is the Entity for the Rooms Database
 */
@Entity
public class Ride {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String date;
    private double rideLengthKm;
    private double averageSpeed;
    private double totalRideTime;
    @TypeConverters({Converters.class})
    private List<GeoPoint> geoPoints;


    public Ride(String name, String date, double rideLengthKm, double averageSpeed, double totalRideTime, List<GeoPoint> geoPoints) {
        this.name = name;
        this.date = date;
        this.rideLengthKm = rideLengthKm;
        this.averageSpeed = averageSpeed;
        this.totalRideTime = totalRideTime;
        this.geoPoints = geoPoints;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public double getRideLengthKm()
    {
        return rideLengthKm;
    }

    public void setRideLengthKm(double rideLengthKm)
    {
        this.rideLengthKm = rideLengthKm;
    }

    public double getAverageSpeed()
    {
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed)
    {
        this.averageSpeed = averageSpeed;
    }

    public double getTotalRideTime()
    {
        return totalRideTime;
    }

    public void setTotalRideTime(double totalRideTime)
    {
        this.totalRideTime = totalRideTime;
    }

    public List<GeoPoint> getGeoPoints()
    {
        return geoPoints;
    }

    public void setGeoPoints(List<GeoPoint> geoPoints)
    {
        this.geoPoints = geoPoints;
    }

    @Override
    public String toString() {
        return "Ride{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", rideLengthKm=" + rideLengthKm +
                ", averageSpeed=" + averageSpeed +
                ", totalRideTime=" + totalRideTime +
                ", geoPoints=" + geoPoints +
                '}';
    }
}
