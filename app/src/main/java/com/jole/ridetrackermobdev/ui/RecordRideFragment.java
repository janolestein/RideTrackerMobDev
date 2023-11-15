package com.jole.ridetrackermobdev.ui;


import static com.jole.ridetrackermobdev.controller.RecordRideService.isRunning;


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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jole.ridetrackermobdev.R;
import com.jole.ridetrackermobdev.controller.MainFragmentsViewModel;
import com.jole.ridetrackermobdev.controller.RecordRideService;


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


        btnStartRecord.setOnClickListener(v ->
        {
            getActivity().startForegroundService((new Intent(getActivity(), RecordRideService.class)));
            btnStartRecord.setVisibility(View.GONE);
            btnStopRecord.setVisibility(View.VISIBLE);
        });

        btnStopRecord.setOnClickListener(v ->
        {
            getActivity().stopService(new Intent(getActivity(), RecordRideService.class));
            btnStartRecord.setVisibility(View.VISIBLE);
            btnStopRecord.setVisibility(View.GONE);
        });

        mainFragmentsViewModel.getUiState().observe(getActivity(), new Observer<double[]>() {
            @Override
            public void onChanged(double[] doubles) {
                tvDistanceVar.setText(Double.toString(doubles[0]));
                tvElapsedTimeVar.setText(Double.toString(doubles[1] / 1000));
                tvAverageSpeedVar.setText(Double.toString(doubles[2]));

            }
        });


        super.onViewCreated(view, savedInstanceState);

//        receiver = new BroadcastReceiver()
//        {
//            @Override
//            public void onReceive(Context context, Intent intent)
//            {
//                double dist = intent.getDoubleExtra("distance", -1);
//                double elapsedTime = intent.getDoubleExtra("elapsedTime", -1);
//                double avSpeed = intent.getDoubleExtra("avSpeed", -1);
//
//                tvDistanceVar.setText(Double.toString(dist));
//                tvAverageSpeedVar.setText(Double.toString(avSpeed));
//                tvElapsedTimeVar.setText(Double.toString(elapsedTime / 1000));
//            }
//        };

    }

    @Override
    public void onResume()
    {
        super.onResume();


    }

    @Override
    public void onStop()
    {
        super.onStop();
    }


}