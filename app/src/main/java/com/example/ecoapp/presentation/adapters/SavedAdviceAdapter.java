package com.example.ecoapp.presentation.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecoapp.data.models.Advice;
import com.example.ecoapp.R;
import com.example.ecoapp.databinding.NearbyLayoutBinding;
import com.example.ecoapp.databinding.SavedAdviceLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SavedAdviceAdapter extends RecyclerView.Adapter<SavedAdviceAdapter.SavedAdviceViewHolder> {

    private int theme;
    private List<Advice> savedAdviceList;

    public SavedAdviceAdapter(List<Advice> savedAdviceList, int theme) {
        this.savedAdviceList = savedAdviceList;
        this.theme = theme;
    }

    @NonNull
    @Override
    public SavedAdviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SavedAdviceLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.saved_advice_layout , parent , false);
        binding.setThemeInfo(theme);
        return new SavedAdviceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedAdviceViewHolder holder, int position) {
        holder.binding.savedAdviceNameTv.setText(savedAdviceList.get(position).getName());
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("guideID", savedAdviceList.get(position).getId());
            Navigation.findNavController(v).navigate(R.id.guideFragment, bundle);
        });
        String url = "http://178.21.8.29:8080/image/" + savedAdviceList.get(position).getImage();
        Picasso.get().load(url).into(holder.binding.savedAdviceImage);
    }

    @Override
    public int getItemCount() {
        return savedAdviceList.size();
    }

    public class SavedAdviceViewHolder extends RecyclerView.ViewHolder {
        private SavedAdviceLayoutBinding binding;

        public SavedAdviceViewHolder(@NonNull SavedAdviceLayoutBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
