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

import com.example.ecoapp.data.models.Search;
import com.example.ecoapp.R;
import com.example.ecoapp.databinding.FoundLayoutBinding;
import com.example.ecoapp.databinding.HabitsLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private int theme;
    private List<Search> searchList;
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void OnItemClick(Search search);
    }


    public SearchAdapter(List<Search> searchList, int theme) {
        this.searchList = searchList;
        this.theme = theme;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FoundLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.found_layout , parent , false);
        binding.setThemeInfo(theme);
        return new SearchViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.binding.foundNameTv.setText(searchList.get(position).getName());
        String url = "http://178.21.8.29:8080/image/" + searchList.get(position).getImage();
        Picasso.get().load(url).into(holder.binding.foundImage);
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        private FoundLayoutBinding binding;

        public SearchViewHolder(@NonNull FoundLayoutBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

            itemView.setOnClickListener(View -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.OnItemClick(searchList.get(position));
                    }
                }
            });
        }
    }
}
