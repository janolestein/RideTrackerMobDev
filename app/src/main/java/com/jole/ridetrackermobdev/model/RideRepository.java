package com.jole.ridetrackermobdev.model;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Delete;
import androidx.room.Query;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


public class RideRepository implements RideRepositoryInterface
{

    DaoInterface rideDao;
    private LiveData<List<Ride>> allRides;
    private MutableLiveData<double[]> rideServiceUiState = new MutableLiveData<double[]>(new double[]{0, 0, 0});



    @Inject
    public RideRepository(DaoInterface rideDao)
    {
        this.rideDao = rideDao;
        allRides = rideDao.getAllRidesList();

    }

    public LiveData<double[]> getRideServiceUiState() {

        return rideServiceUiState;
    }

    public void setRideServiceUiState(double[] rideServiceUiState) {
        this.rideServiceUiState.setValue(rideServiceUiState);
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
