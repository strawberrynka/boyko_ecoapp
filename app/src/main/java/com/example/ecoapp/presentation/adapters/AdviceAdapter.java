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
import com.example.ecoapp.databinding.AdviceLayoutBinding;
import com.example.ecoapp.databinding.CommentLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdviceAdapter extends RecyclerView.Adapter<AdviceAdapter.AdviceViewHolder> {
    private List<Advice> adviceList;
    private int theme;

    public AdviceAdapter(List<Advice> adviceList, int theme) {
        this.adviceList = adviceList;
        this.theme = theme;
    }

    @NonNull
    @Override
    public AdviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdviceLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.advice_layout , parent , false);
        binding.setThemeInfo(theme);
        return new AdviceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdviceViewHolder holder, int position) {
        holder.binding.adviceNameTv.setText(adviceList.get(position).getName());
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("guideID", adviceList.get(position).getId());
            Navigation.findNavController(v).navigate(R.id.guideFragment, bundle);
        });
        String url = "http://178.21.8.29:8080/image/" + adviceList.get(position).getImage();
        Picasso.get().load(url).into(holder.binding.adviceImage);
    }

    @Override
    public int getItemCount() {
        return adviceList.size();
    }

    public class AdviceViewHolder extends RecyclerView.ViewHolder {
        private AdviceLayoutBinding binding;

        public AdviceViewHolder(@NonNull AdviceLayoutBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
