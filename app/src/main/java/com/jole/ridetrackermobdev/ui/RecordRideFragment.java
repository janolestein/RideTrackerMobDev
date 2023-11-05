package com.jole.ridetrackermobdev.ui;




import static com.jole.ridetrackermobdev.controller.RecordRideService.isRunning;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jole.ridetrackermobdev.R;
import com.jole.ridetrackermobdev.controller.RecordRideService;


public class RecordRideFragment extends Fragment {
    private Button btnStartRecord, btnStopRecord;
    private BroadcastReceiver receiver;
    private TextView tvDistanceVar, tvAverageSpeedVar;

    public RecordRideFragment() {}


    public static RecordRideFragment newInstance(String param1, String param2) {
        RecordRideFragment fragment = new RecordRideFragment();


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_record_ride, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        btnStartRecord = getView().findViewById(R.id.btnStartRecord);
        btnStopRecord = getView().findViewById(R.id.btnStopRecord);
        tvDistanceVar = getView().findViewById(R.id.tvDistanceVar);
        tvAverageSpeedVar = getView().findViewById(R.id.tvAverageSpeedVar);

        if (isRunning == false || isRunning == null)
        {
            btnStartRecord.setVisibility(View.VISIBLE);
            btnStopRecord.setVisibility(View.GONE);
        }
        if (isRunning == true)
        {
            btnStartRecord.setVisibility(View.GONE);
            btnStopRecord.setVisibility(View.VISIBLE);
        }


        btnStartRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startForegroundService((new Intent(getActivity(), RecordRideService.class)));
                btnStartRecord.setVisibility(View.GONE);
                btnStopRecord.setVisibility(View.VISIBLE);
            }
        });

        btnStopRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().stopService(new Intent(getActivity(), RecordRideService.class));
                btnStartRecord.setVisibility(View.VISIBLE);
                btnStopRecord.setVisibility(View.GONE);
            }
        });
        


        super.onViewCreated(view, savedInstanceState);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Double latitude = intent.getDoubleExtra("latitude", -1);
                Double longitude = intent.getDoubleExtra("longitude", -1);

                tvDistanceVar.setText(Double.toString(latitude));
                tvAverageSpeedVar.setText(Double.toString(longitude));
            }
        };

    }

    @Override
    public void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((receiver),
                new IntentFilter("loc")
        );
    }

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        super.onStop();
    }



}