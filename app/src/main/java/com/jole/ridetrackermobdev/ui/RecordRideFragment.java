package com.jole.ridetrackermobdev.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jole.ridetrackermobdev.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecordRideFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordRideFragment extends Fragment {


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
}