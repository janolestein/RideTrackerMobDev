package com.jole.ridetrackermobdev.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jole.ridetrackermobdev.R;
import com.jole.ridetrackermobdev.model.Model;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrackedRidesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrackedRidesFragment extends Fragment
{
    private RecyclerView recViewAllRides;
    private RideItemRecViewAdapter adapter;

    public static TrackedRidesFragment newInstance(String param1, String param2)
    {
        TrackedRidesFragment fragment = new TrackedRidesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tracked_rides, container, false);

        adapter = new RideItemRecViewAdapter(view.getContext());
        recViewAllRides = view.findViewById(R.id.recViewAllRides);
        recViewAllRides.setAdapter(adapter);
        recViewAllRides.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter.setRides(Model.getInstance().getAllRidesList());

        return view;
    }

}