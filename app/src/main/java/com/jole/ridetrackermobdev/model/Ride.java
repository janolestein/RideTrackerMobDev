package com.jole.ridetrackermobdev.model;

import java.time.LocalDate;
import java.util.Date;

public class Ride {
    private int id;
    private String name;
    private String Description;
    private LocalDate date;
    private double rideLengthKm;
    private String imgUrl;

    public Ride(int id, String name, String description, LocalDate date, double rideLengthKm, String imgUrl) {
        this.id = id;
        this.name = name;
        Description = description;
        this.date = date;
        this.rideLengthKm = rideLengthKm;
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
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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
}
