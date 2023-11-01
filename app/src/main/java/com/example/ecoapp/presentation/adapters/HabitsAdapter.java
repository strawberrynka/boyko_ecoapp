package com.example.ecoapp.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecoapp.data.models.Habit;
import com.example.ecoapp.R;
import com.example.ecoapp.databinding.ComingLayoutBinding;
import com.example.ecoapp.databinding.HabitsLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class HabitsAdapter extends RecyclerView.Adapter<HabitsAdapter.HabitsViewHolder> {

    private List<Habit> habitsList;
    private OnItemClickListener listener;
    private int theme;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public HabitsAdapter(List<Habit> habitsList, int theme) {
        this.habitsList = habitsList;
        this.theme = theme;
    }

    public void updateList(ArrayList<Habit> habitsList) {
        this.habitsList = habitsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HabitsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HabitsLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.habits_layout , parent , false);
        binding.setThemeInfo(theme);
        return new HabitsViewHolder(binding);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull HabitsViewHolder holder, int position) {
        holder.binding.habitTv.setText(habitsList.get(position).getTitle());
        if (habitsList.get(position).isDone()) holder.binding.habitCircleCheck.setImageResource(R.drawable.green_check);
        else holder.binding.habitCircleCheck.setImageResource(R.drawable.grey_circle);
    }

    @Override
    public int getItemCount() {
        return habitsList.size();
    }

    public class HabitsViewHolder extends RecyclerView.ViewHolder{
        private HabitsLayoutBinding binding;

        public HabitsViewHolder(@NonNull HabitsLayoutBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

            binding.habitCircleCheck.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}