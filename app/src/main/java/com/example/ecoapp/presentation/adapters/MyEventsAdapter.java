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

import com.example.ecoapp.data.models.MyEvents;
import com.example.ecoapp.R;
import com.example.ecoapp.databinding.HabitsLayoutBinding;
import com.example.ecoapp.databinding.MyEventsLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyEventsAdapter extends RecyclerView.Adapter<MyEventsAdapter.MyEventsViewHolder> {
    private int theme;
    private List<MyEvents> myEventsList;

    public MyEventsAdapter(List<MyEvents> myEventsList, int theme) {
        this.myEventsList = myEventsList;
        this.theme = theme;
    }

    @NonNull
    @Override
    public MyEventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyEventsLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.my_events_layout , parent , false);
        binding.setThemeInfo(theme);
        return new MyEventsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyEventsViewHolder holder, int position) {
        holder.binding.myEventNameTv.setText(myEventsList.get(position).getName());

        String url = "http://178.21.8.29:8080/image/" + myEventsList.get(position).getImage();
        Picasso.get().load(url).into(holder.binding.myEventImage);

        holder.binding.eventsCardViewMy.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", myEventsList.get(position).getId());
            Navigation.findNavController(v).navigate(R.id.eventFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return myEventsList.size();
    }

    public class MyEventsViewHolder extends RecyclerView.ViewHolder {
        private MyEventsLayoutBinding binding;

        public MyEventsViewHolder(@NonNull MyEventsLayoutBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}