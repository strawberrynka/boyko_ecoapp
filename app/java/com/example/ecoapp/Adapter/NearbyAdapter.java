package com.example.ecoapp.Adapter;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecoapp.Model.Nearby;
import com.example.ecoapp.R;

import java.util.List;

public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.NearbyViewHolder> {

    private List<Nearby> nearbyList;
    public NearbyAdapter(List<Nearby> nearbyList){
        this.nearbyList = nearbyList;
    }
    @NonNull
    @Override
    public NearbyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nearby_layout , parent , false);
        return new NearbyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NearbyViewHolder holder, int position) {
        holder.mName.setText(nearbyList.get(position).getName());
        holder.mImageview.setImageResource(nearbyList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return nearbyList.size();
    }

    public class NearbyViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImageview;
        private TextView mName;
        public NearbyViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageview = itemView.findViewById(R.id.nearby_image);
            mName = itemView.findViewById(R.id.nearby_name_tv);
        }
    }
}