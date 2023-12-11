package com.jole.ridetrackermobdev.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jole.ridetrackermobdev.R;
import com.jole.ridetrackermobdev.model.DaoInterface;

import com.jole.ridetrackermobdev.model.Ride;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Recycle View Adapter for Ride Objects
 */
public class RideItemRecViewAdapter extends RecyclerView.Adapter<RideItemRecViewAdapter.ViewHolder>{

    private List<Ride> rideList = new ArrayList<>();

    private Context context;

    public RideItemRecViewAdapter(@NonNull Context context)
    {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ride_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        holder.tvTitelRide.setText(rideList.get(position).getName());
        holder.tvDate.setText(rideList.get(position).getDate().toString());
        holder.tvDesc.setText(rideList.get(position).getDescription());

        Glide.with(context)
                .asBitmap()
                .load(rideList.get(position).getImgUrl())
                .into(holder.ivRideScreenShot);

        holder.rideItemCard.setOnClickListener(v ->
        {
            Intent intent = new Intent(context, RideDetailActivity.class);
            intent.putExtra("RideId", rideList.get(position).getId());
            context.startActivity(intent);

        });

    }

    @Override
    public int getItemCount()
    {
        return rideList.size();
    }

    public void setRides(List<Ride> rides)
    {
        this.rideList = rides;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView ivRideScreenShot;
        private TextView tvTitelRide, tvDate, tvDesc;
        private CardView rideItemCard;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            this.ivRideScreenShot = itemView.findViewById(R.id.ivRideScreenShot);
            this.tvTitelRide = itemView.findViewById(R.id.tvTitelRide);
            this.tvDate = itemView.findViewById(R.id.tvDateDetail);
            this.tvDesc = itemView.findViewById(R.id.tvDescDetail);
            this.rideItemCard = itemView.findViewById(R.id.rideItemCard);
        }
    }

}
