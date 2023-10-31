package com.jole.ridetrackermobdev.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jole.ridetrackermobdev.R;
import com.jole.ridetrackermobdev.controller.RecordRideService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecordRideFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordRideFragment extends Fragment {
    private Button btnStartRecord, btnStopRecord;

    public RecordRideFragment() {
        // Required empty public constructor
    }


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
        btnStopRecord.setVisibility(View.GONE);

        btnStartRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startService(new Intent(getActivity(), RecordRideService.class));
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



    }
}