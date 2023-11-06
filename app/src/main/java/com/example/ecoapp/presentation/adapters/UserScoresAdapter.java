package com.example.ecoapp.presentation.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecoapp.R;
import com.example.ecoapp.data.models.User;
import com.example.ecoapp.databinding.UsersScoresItemBinding;

import java.util.List;

public class UserScoresAdapter extends RecyclerView.Adapter<UserScoresAdapter.UserScoresViewHolder> {
    private List<User> userScoresList;
    private OnItemClickListener listener;
    private int theme;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    public UserScoresAdapter(List<User> userScoresList, int theme) {
        this.userScoresList = userScoresList;
        this.theme = theme;
    }

    public void deleteItem(String id) {
        for (int i = 0; i < userScoresList.size(); i++) {
            if (userScoresList.get(i).getId().equals(id)) {
                userScoresList.remove(i);
                notifyDataSetChanged();
                break;
            }
        }
    }

    @NonNull
    @Override
    public UserScoresAdapter.UserScoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UsersScoresItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.users_scores_item , parent , false);
        binding.setThemeInfo(theme);

        return new UserScoresAdapter.UserScoresViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserScoresViewHolder holder, int position) {
        holder.binding.usersScoresName.setText(userScoresList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return userScoresList.size();
    }

    public User getItem(int position) {
        return userScoresList.get(position);
    }

    public class UserScoresViewHolder extends RecyclerView.ViewHolder {
        private UsersScoresItemBinding binding;

        public UserScoresViewHolder(@NonNull UsersScoresItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

            binding.usersScoresConfirm.setOnClickListener(V -> {
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