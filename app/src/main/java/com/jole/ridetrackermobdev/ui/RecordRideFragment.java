package com.jole.ridetrackermobdev.ui;


import static com.jole.ridetrackermobdev.controller.RecordRideService.isRunning;


import static java.lang.Math.round;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jole.ridetrackermobdev.R;
import com.jole.ridetrackermobdev.controller.MainFragmentsViewModel;
import com.jole.ridetrackermobdev.controller.RecordRideService;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Fragment that Records the Ride, Button Stars the Service. Shows current Data of Service of it is running
 */
@AndroidEntryPoint
public class RecordRideFragment extends Fragment
{
    private Button btnStartRecord, btnStopRecord;
    private BroadcastReceiver receiver;
    private TextView tvDistanceVar, tvAverageSpeedVar, tvElapsedTimeVar;
    private MainFragmentsViewModel mainFragmentsViewModel;

    public RecordRideFragment()
    {
    }


    public static RecordRideFragment newInstance()
    {
        RecordRideFragment fragment = new RecordRideFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        mainFragmentsViewModel = new ViewModelProvider(requireActivity()).get(MainFragmentsViewModel.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_record_ride, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        btnStartRecord = getView().findViewById(R.id.btnStartRecord);
        btnStopRecord = getView().findViewById(R.id.btnStopRecord);
        tvDistanceVar = getView().findViewById(R.id.tvDistanceVar);
        tvAverageSpeedVar = getView().findViewById(R.id.tvAverageSpeedVar);
        tvElapsedTimeVar = getView().findViewById(R.id.tvElapsedTimeVar);


        if (!isRunning)
        {
            btnStartRecord.setVisibility(View.VISIBLE);
            btnStopRecord.setVisibility(View.GONE);
        }
        if (isRunning)
        {
            btnStartRecord.setVisibility(View.GONE);
            btnStopRecord.setVisibility(View.VISIBLE);
        }

        //Onclick listener on the Start Button to Start the RecordRideService
        btnStartRecord.setOnClickListener(v ->
        {
            getActivity().startForegroundService((new Intent(getActivity(), RecordRideService.class)));
            btnStartRecord.setVisibility(View.GONE);
            btnStopRecord.setVisibility(View.VISIBLE);
        });

        //Onclick listener on the Stop Button to Stop the RecordRideService
        btnStopRecord.setOnClickListener(v ->
        {
            getActivity().stopService(new Intent(getActivity(), RecordRideService.class));
            btnStartRecord.setVisibility(View.VISIBLE);
            btnStopRecord.setVisibility(View.GONE);
        });
        /**
         * Observes the LiveData of the State generated by the Service to display all changes
         */
        mainFragmentsViewModel.getUiState().observe(getViewLifecycleOwner(), new Observer<double[]>()
        {
            @Override
            public void onChanged(double[] doubles)
            {
                Log.v("ABC", "onChanged");
                tvDistanceVar.setText(String.format(Locale.GERMANY, "%.2f",doubles[0]) + " km");
                tvElapsedTimeVar.setText(String.format(Locale.GERMANY,"%dh, %dmin, %dsec",
                        TimeUnit.MILLISECONDS.toHours((long) doubles[1]),
                        TimeUnit.MILLISECONDS.toMinutes((long) doubles[1]),
                        TimeUnit.MILLISECONDS.toSeconds((long) doubles[1]) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) doubles[1]))
                ));
                tvAverageSpeedVar.setText(String.format(Locale.GERMANY, "%.2f",doubles[2]) + " km/h");

            }
        });


        super.onViewCreated(view, savedInstanceState);

    }

}