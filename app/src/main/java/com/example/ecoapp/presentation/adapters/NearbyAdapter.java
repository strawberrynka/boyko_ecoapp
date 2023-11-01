package com.example.ecoapp.presentation.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecoapp.data.models.EventCustom;
import com.example.ecoapp.R;
import com.example.ecoapp.databinding.HabitsLayoutBinding;
import com.example.ecoapp.databinding.NearbyLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.NearbyViewHolder> {

    private int theme;
    private List<EventCustom> nearbyList;

    public NearbyAdapter(List<EventCustom> nearbyList, int theme) {
        this.nearbyList = nearbyList;
        this.theme = theme;
    }

    @NonNull
    @Override
    public NearbyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NearbyLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.nearby_layout , parent , false);
        binding.setThemeInfo(theme);
        return new NearbyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NearbyViewHolder holder, int position) {
        holder.binding.nearbyNameTv.setText(nearbyList.get(position).getTitle());

        String url = "http://178.21.8.29:8080/image/" + nearbyList.get(position).getPhoto();
        Picasso.get().load(url).into(holder.binding.nearbyImage);

        holder.binding.nearbyItem.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", nearbyList.get(position).getEventID());

            Navigation.findNavController(v).navigate(R.id.eventFragment, bundle);
        });
    }

    public void updateAdverts(List<EventCustom> events) {
        try {
            this.nearbyList.addAll(events);

            notifyDataSetChanged();
        } catch (NullPointerException err) {
            err.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return nearbyList.size();
    }

    public class NearbyViewHolder extends RecyclerView.ViewHolder{

        private NearbyLayoutBinding binding;

        public NearbyViewHolder(@NonNull NearbyLayoutBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
//            mImageview = itemView.findViewById(R.id.nearby_image);
//            mName = itemView.findViewById(R.id.nearby_name_tv);
//            cardView = itemView.findViewById(R.id.nearby_item);
        }
    }
}