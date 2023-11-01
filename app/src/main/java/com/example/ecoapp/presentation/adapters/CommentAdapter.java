package com.example.ecoapp.presentation.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecoapp.data.models.Comment;
import com.example.ecoapp.R;
import com.example.ecoapp.databinding.CommentLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> commentList;
    private int theme;
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(String commentID);
    }


    public CommentAdapter(List<Comment> commentList, int theme) {
        this.commentList = commentList;
        this.theme = theme;
    }
    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CommentLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.comment_layout , parent , false);
        binding.setThemeInfo(theme);
        return new CommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.binding.commentProfileNameTv.setText(commentList.get(position).getProfileName());
        holder.binding.commentDate.setText(commentList.get(position).getDate());
        holder.binding.commentContentTv.setText(commentList.get(position).getContent());

        String url = "http://178.21.8.29:8080/image/" + commentList.get(position).getProfileImage();
        Picasso.get().load(url).into(holder.binding.commentProfileImage);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public void deleteItem(String id) {
        for (int i = 0; i < commentList.size(); i++) {
            if (commentList.get(i).getId().equals(id)) {
                commentList.remove(i);
                notifyDataSetChanged();
                break;
            }
        }
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        private CommentLayoutBinding binding;
        public CommentViewHolder(@NonNull CommentLayoutBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
            binding.deleteComment.setOnClickListener(View -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(commentList.get(position).getId());
                    }
                }
            });
        }
    }
}