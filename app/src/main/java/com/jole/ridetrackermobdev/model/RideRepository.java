package com.jole.ridetrackermobdev.model;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Query;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


public class RideRepository
{

    DaoInterface rideDao;
    private LiveData<List<Ride>> allRides;



    public RideRepository(DaoInterface rideDao)
    {
        this.rideDao = rideDao;
        allRides = rideDao.getAllRidesList();
    }

    public void addNewRide(Ride ride)
    {
        new InsertRideAsyncTask(rideDao).execute(ride);
    }

    public void removeRide(Ride ride)
    {
        new RemoveRideAsyncTask(rideDao).execute(ride);
    }

    public LiveData<List<Ride>> getAllRidesList()
    {
        return allRides;
    }

    public Optional<Ride> findRideById(int id)
    {
        try
        {
            return new FindRideByIdRideAsyncTask(rideDao).execute(id).get();
        } catch (ExecutionException e)
        {
            throw new RuntimeException(e);
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }


    private static class InsertRideAsyncTask extends AsyncTask<Ride, Void, Void>
    {
        private DaoInterface rideDao;

        private InsertRideAsyncTask(DaoInterface rideDao)
        {
            this.rideDao = rideDao;
        }

        @Override
        protected Void doInBackground(Ride... rides)
        {
            rideDao.addNewRide(rides[0]);
            return null;
        }
    }
    private static class RemoveRideAsyncTask extends AsyncTask<Ride, Void, Void>
    {
        private DaoInterface rideDao;

        private RemoveRideAsyncTask(DaoInterface rideDao)
        {
            this.rideDao = rideDao;
        }

        @Override
        protected Void doInBackground(Ride... rides)
        {
            rideDao.removeRide(rides[0]);
            return null;
        }
    }
    private static class FindRideByIdRideAsyncTask extends AsyncTask<Integer, Void,  Optional<Ride>>
    {
        private DaoInterface rideDao;

        private FindRideByIdRideAsyncTask(DaoInterface rideDao)
        {
            this.rideDao = rideDao;
        }


        @Override
        protected Optional<Ride> doInBackground(Integer... integers)
        {
            return rideDao.findRideById(integers[0]);
        }
    }

}
