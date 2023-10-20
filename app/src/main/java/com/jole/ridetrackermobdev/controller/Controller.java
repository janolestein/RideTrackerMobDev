package com.jole.ridetrackermobdev.controller;

import com.jole.ridetrackermobdev.model.Model;
import com.jole.ridetrackermobdev.model.ModelInterface;
import com.jole.ridetrackermobdev.model.Ride;

import java.util.ArrayList;

public class Controller implements ControllerInterface
{
    private static Controller instance;

    private static ModelInterface model;

    private Controller()
    {

    }

    public static Controller getInstance()
    {
        if (instance == null)
        {
            model = Model.getInstance();
            instance = new Controller();
        }

        return instance;
    }

    public ArrayList<Ride> getAllRidesList()
    {
        return model.getAllRidesList();
    }


}
