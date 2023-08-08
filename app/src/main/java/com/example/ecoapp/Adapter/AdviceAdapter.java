package com.example.ecoapp.Adapter;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecoapp.Model.Advice;
import com.example.ecoapp.R;

import java.util.List;

public class AdviceAdapter extends RecyclerView.Adapter<AdviceAdapter.AdviceViewHolder> {

    private List<Advice> adviceList;

    public AdviceAdapter(List<Advice> adviceList) {
        this.adviceList = adviceList;
    }

    @NonNull
    @Override
    public AdviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.advice_layout, parent, false);
        return new AdviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdviceViewHolder holder, int position) {
        holder.name.setText(adviceList.get(position).getName());
        holder.mImageView.setImageResource(adviceList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return adviceList.size();
    }

    public class AdviceViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView mImageView;
        public AdviceViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.advice_name_tv);
            mImageView = itemView.findViewById(R.id.advice_image);
        }
    }
}
