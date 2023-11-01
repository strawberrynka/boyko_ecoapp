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

import com.example.ecoapp.data.models.Coming;
import com.example.ecoapp.R;
import com.example.ecoapp.databinding.ComingLayoutBinding;
import com.example.ecoapp.databinding.CommentLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ComingAdapter extends RecyclerView.Adapter<ComingAdapter.ComingViewHolder> {
    private List<Coming> comingList;
    private int theme;

    public ComingAdapter(List<Coming> comingList, int theme) {
        this.comingList = comingList;
        this.theme = theme;
    }
    @NonNull
    @Override
    public ComingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ComingLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.coming_layout , parent , false);
        binding.setThemeInfo(theme);
        return new ComingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ComingViewHolder holder, int position) {
        holder.binding.comingNameTv.setText(comingList.get(position).getName());

        String url = "http://178.21.8.29:8080/image/" + comingList.get(position).getImage();
        Picasso.get().load(url).into(holder.binding.comingImage);

        holder.binding.comingCardViewWrapper.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", comingList.get(position).getId());
            Navigation.findNavController(v).navigate(R.id.eventFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return comingList.size();
    }

    public class ComingViewHolder extends RecyclerView.ViewHolder {
        private ComingLayoutBinding binding;

        public ComingViewHolder(@NonNull ComingLayoutBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}