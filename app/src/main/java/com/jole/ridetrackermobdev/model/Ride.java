package com.jole.ridetrackermobdev.model;

import java.time.LocalDate;

public class Ride {
    private int id;
    private String name;
    private String description;
    private LocalDate date;
    private double rideLengthKm;
    private double averageSpeed;
    private double totalRideTime;
    private String imgUrl;


    public Ride(int id, String name, String description, LocalDate date, double rideLengthKm, double averageSpeed, double totalRideTime, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.rideLengthKm = rideLengthKm;
        this.averageSpeed = averageSpeed;
        this.totalRideTime = totalRideTime;
        this.imgUrl = imgUrl;
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
}
