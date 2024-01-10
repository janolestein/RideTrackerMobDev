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
    /**
     * Id as Integer, not used in the Constructor only set by the Rooms Database
     */
    @PrimaryKey(autoGenerate = true)
    private int id;
    /**
     * The Name or Titel of the Ride as a String
     */
    private String name;
    /**
     * Date of the Ride as a String
     */
    private String date;
    /**
     * Total length of the Ride as a Double
     */
    private double rideLengthKm;
    /**
     * Average Speed of the Ride as a Double
     */
    private double averageSpeed;
    /**
     * Total Time of the Ride as a Double
     */
    private double totalRideTime;
    /**
     * List of GeoPoints that got recorded during the Ride
     */
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
